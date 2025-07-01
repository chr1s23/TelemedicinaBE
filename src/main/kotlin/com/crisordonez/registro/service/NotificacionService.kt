package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.DispositivoAppUsuarioEntity
import com.crisordonez.registro.model.entities.NotificacionEntity
import com.crisordonez.registro.model.enums.TipoNotificacionEnum
import com.crisordonez.registro.model.mapper.NotificacionMapper.toEntity
import com.crisordonez.registro.model.mapper.NotificacionMapper.toResponse
import com.crisordonez.registro.model.mapper.NotificacionProgramadaMapper.toEntity
import com.crisordonez.registro.repository.NotificacionProgramadaRepository
import com.crisordonez.registro.repository.NotificacionRepository
import com.crisordonez.registro.model.requests.NotificacionProgramadaRequest
import com.crisordonez.registro.model.requests.NotificacionRequest
import com.crisordonez.registro.model.responses.NotificacionResponse
import com.crisordonez.registro.repository.CuentaUsuarioRepository
import com.crisordonez.registro.repository.DispositivoAppUsuarioRepository
import com.crisordonez.registro.repository.ExamenVphRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID
import java.time.Duration


@Service
class NotificacionService(
    private val notificacionRepository: NotificacionRepository,
    private val notificacionProgramadaRepository: NotificacionProgramadaRepository,
    private val cuentaUsuarioRepository: CuentaUsuarioRepository,
    private val pushNotificacionService: PushNotificacionServiceInterface,
    private val dispositivoAppUsuarioRepository: DispositivoAppUsuarioRepository,
    private val examenVphRepository: ExamenVphRepository,
) : NotificacionServiceInterface {

    private val logger = LoggerFactory.getLogger(NotificacionService::class.java)

    @Transactional
    override fun crearNotificacion(request: NotificacionRequest): NotificacionResponse {
        val cuentaUsuario = cuentaUsuarioRepository.findByPublicId(request.cuentaUsuarioPublicId)
            .orElseThrow { IllegalArgumentException("CuentaUsuario no encontrado") }

        val notificacion = request.toEntity(cuentaUsuario)
        val guardada = notificacionRepository.save(notificacion)


        //Recuperamos el token del dispositivo
        val dispositivo = dispositivoAppUsuarioRepository
            .findTopByUsuarioPublicIdOrderByFechaRegistroDesc(cuentaUsuario.publicId)

        val token = dispositivo?.fcmToken
        logger.info("El token del dispositivo es: $token")

        if (token != null) {
            val notificacionResponse = guardada.toResponse()
            pushNotificacionService.enviarPushFCM(
                token,
                notificacionResponse
            )
        }
        else {
            logger.warn("[!] No se pudo enviar notificación push: el usuario no tiene token FCM registrado")
        }

        return guardada.toResponse()
    }

    override fun obtenerHistorialNotificaciones(cuentaUsuarioPublicId: UUID): List<NotificacionResponse> {
        val notificaciones =
            notificacionRepository.findAllByCuentaUsuarioPublicIdOrderByFechaCreacionDesc(cuentaUsuarioPublicId)
        return notificaciones.map { it.toResponse() }
    }

    override fun marcarNotificacionComoLeida(publicId: UUID) {
        val notificacion = notificacionRepository.findByPublicId(publicId)
            .orElseThrow { IllegalArgumentException("Notificación no encontrada para ser marcada como Leída") }

        notificacion.notificacion_leida = true
        notificacionRepository.save(notificacion)
    }
    override fun desactivarRecordatorioNoEntregaDispositivo(cuentaUsuarioPublicId: UUID) {
        val notificacion = notificacionProgramadaRepository
            .findActivaByCuentaUsuarioPublicIdAndTipoNotificacion(
                cuentaUsuarioPublicId,
                TipoNotificacionEnum.RECORDATORIO_NO_ENTREGA_DISPOSITIVO
            )
            .orElse(null)

        if (notificacion != null && notificacion.programacionActiva) {
            notificacionProgramadaRepository.updateActivaById(false, notificacion.id)
            println("[OK] Notificación desactivada.")
        } else {
            println("[!] Ya estaba desactivada o no existe. No se realizó ningún cambio.")
        }
    }


    @Transactional
    override fun crearNotificacionProgramada(
        requestNotificacion: NotificacionRequest,
        requestProgramada: NotificacionProgramadaRequest
    ): NotificacionResponse {
        val cuentaUsuario = cuentaUsuarioRepository.findByPublicId(requestNotificacion.cuentaUsuarioPublicId)
            .orElseThrow { IllegalArgumentException("CuentaUsuario no encontrado - creación programada") }

        val programada = requestProgramada.toEntity(cuentaUsuario)
        val guardada = notificacionProgramadaRepository.save(programada)

        logger.info("[Programada] Notificación programada para ${cuentaUsuario.id} a partir del ${programada.proxFecha}")
        return NotificacionResponse(
            publicId = guardada.publicId,
            cuentaUsuarioPublicId = cuentaUsuario.publicId,
            titulo = guardada.titulo,
            mensaje = guardada.mensaje,
            tipoNotificacion = guardada.tipoNotificacion,
            tipoAccion = guardada.tipoAccion,
            accion = guardada.accion,
            fechaCreacion = guardada.proxFecha, // como es plantilla, su "creación" es el inicio
            notificacionLeida = false // solo se marca en instancias reales
        )
    }

    @Transactional
    override fun procesarNotificacionesProgramadas() {
        val ahora = LocalDateTime.now()
        val pendientes = notificacionProgramadaRepository.findAllByProxFechaBeforeAndProgramacionActivaIsTrue(ahora)



        pendientes.forEach { prog ->
            val cuentaUsuario = prog.cuentaUsuario
            if (prog.tipoNotificacion == TipoNotificacionEnum.RECORDATORIO_NO_EXAMEN) {
                val paciente = prog.cuentaUsuario.paciente
                if (paciente != null && examenVphRepository.existsExamenByPacienteId(paciente.id!!)) {
                    logger.info("Paciente ${paciente.id} ya tiene examen, se cancela la notificación programada (${prog.publicId})")
                    prog.programacionActiva = false
                    notificacionProgramadaRepository.save(prog)
                    return@forEach
                }
            }

            // 1. Crear notificación real
            crearNotificacion(
                NotificacionRequest(
                    cuentaUsuarioPublicId = cuentaUsuario.publicId,
                    tipoNotificacion = prog.tipoNotificacion,
                    titulo = prog.titulo,
                    mensaje = prog.mensaje,
                    tipoAccion = prog.tipoAccion,
                    accion = prog.accion
                )
            )


            // 2. Calcular el tiempo transcurrido desde inicio
            val diasTranscurridos = java.time.Duration.between(prog.fechaInicio, ahora).toMinutes()
            /*

            // 3. Elegir intervalo dinámico para próximo envío
            val nuevoIntervaloEnDias = when {
                diasTranscurridos < 30 -> 3L  // Primer mes → cada 3 días
                diasTranscurridos < 60 -> 7L  // Segundo mes → cada 7 días
                else -> null                  // Tercer mes → parar
            }



            if (nuevoIntervaloEnDias == null || (prog.limiteFecha != null && ahora.isAfter(prog.limiteFecha))) {
                prog.programacionActiva = false
            } else {
                prog.proxFecha = ahora.plusDays(nuevoIntervaloEnDias)
            }
            */
            val segundosTranscurridos = Duration.between(prog.fechaInicio, ahora).toSeconds()

            val nuevoIntervaloEnSegundos = when {
                segundosTranscurridos < 40 -> 20L  // Simulación: primeros 40s → cada 20s
                segundosTranscurridos < 80 -> 30L  // Después → cada 30s
                else -> null                       // Luego detener
            }

            if (nuevoIntervaloEnSegundos == null || (prog.limiteFecha != null && ahora.isAfter(prog.limiteFecha))) {
                prog.programacionActiva = false
            } else {
                prog.proxFecha = ahora.plusSeconds(nuevoIntervaloEnSegundos)
            }


            notificacionProgramadaRepository.save(prog)
            logger.info("[Programada] Notificación enviada y reprogramada (${prog.titulo}) para ${cuentaUsuario.id}, comenzando en [${prog.fechaInicio}] -> [${prog.proxFecha}]")
        }
    }


}

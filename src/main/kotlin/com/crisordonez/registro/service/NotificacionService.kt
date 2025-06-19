package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.NotificacionEntity
import com.crisordonez.registro.model.mapper.NotificacionMapper.toEntity
import com.crisordonez.registro.model.mapper.NotificacionMapper.toResponse
import com.crisordonez.registro.model.mapper.NotificacionProgramadaMapper.toEntity
import com.crisordonez.registro.repository.NotificacionProgramadaRepository
import com.crisordonez.registro.repository.NotificacionRepository
import com.crisordonez.registro.model.requests.NotificacionProgramadaRequest
import com.crisordonez.registro.model.requests.NotificacionRequest
import com.crisordonez.registro.model.responses.NotificacionResponse
import com.crisordonez.registro.repository.CuentaUsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class NotificacionService(
    private val notificacionRepository: NotificacionRepository,
    private val notificacionProgramadaRepository: NotificacionProgramadaRepository,
    private val cuentaUsuarioRepository: CuentaUsuarioRepository,
    private val pushNotificacionService: PushNotificacionServiceInterface
) : NotificacionServiceInterface {

    private val logger = LoggerFactory.getLogger(NotificacionService::class.java)

    @Transactional
    override fun crearNotificacion(request: NotificacionRequest): NotificacionResponse {
        val cuentaUsuario = cuentaUsuarioRepository.findByPublicId(request.cuentaUsuarioPublicId)
            .orElseThrow { IllegalArgumentException("CuentaUsuario no encontrado") }

        val notificacion = request.toEntity(cuentaUsuario)
        val guardada = notificacionRepository.save(notificacion)

        pushNotificacionService.enviarPush(guardada.titulo, guardada.mensaje, cuentaUsuario)

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

            // 1. Crear notificación real
            val notificacionReal = notificacionRepository.save(
                NotificacionEntity(
                    cuentaUsuario = cuentaUsuario,
                    tipo_notificacion = prog.tipoNotificacion,
                    titulo = prog.titulo,
                    mensaje = prog.mensaje,
                    tipo_accion = prog.tipoAccion,
                    accion = prog.accion,
                    fecha_creacion = ahora,
                    notificacion_leida = false
                )
            )

            // 2. Enviar push (simulado)
            pushNotificacionService.enviarPush(notificacionReal.titulo, notificacionReal.mensaje, cuentaUsuario)

            // 3. Calcular el tiempo transcurrido desde inicio
            val diasTranscurridos = java.time.Duration.between(prog.fechaInicio, ahora).toMinutes()

            // 4. Elegir intervalo dinámico
            val nuevoIntervalo = when {
                diasTranscurridos < 9 -> 3L // Simulación de primer mes (3 min)
                diasTranscurridos < 18 -> 5L // Simulación segundo mes (5 min)
                else -> null // Más de 2 meses, detener
            }

            if (nuevoIntervalo == null) {
                prog.programacionActiva = false
            } else {
                prog.proxFecha = ahora.plusMinutes(nuevoIntervalo)
            }

            notificacionProgramadaRepository.save(prog)
            logger.info("[Programada] Notificación enviada y reprogramada (${prog.publicId}) para ${cuentaUsuario.id}")
        }
    }


}

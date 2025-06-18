package com.crisordonez.registro.service

import com.crisordonez.registro.model.mapper.NotificacionMapper.toEntity
import com.crisordonez.registro.model.mapper.NotificacionMapper.toResponse
import com.crisordonez.registro.model.mapper.NotificacionProgramadaMapper.toEntity
import com.crisordonez.registro.repository.NotificacionProgramadaRepository
import com.crisordonez.registro.repository.NotificacionRepository
import com.crisordonez.registro.repository.PacienteRepository
import com.crisordonez.registro.model.requests.NotificacionProgramadaRequest
import com.crisordonez.registro.model.requests.NotificacionRequest
import com.crisordonez.registro.model.responses.NotificacionResponse
import com.crisordonez.registro.repository.CuentaUsuarioRepository
import com.crisordonez.registro.service.NotificacionServiceInterface
import com.crisordonez.registro.service.PushNotificacionServiceInterface
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
    override fun createNotification(request: NotificacionRequest): NotificacionResponse {
        val cuentaUsuario = cuentaUsuarioRepository.findByPublicId(request.cuentaUsuarioPublicId)
            .orElseThrow { IllegalArgumentException("CuentaUsuario no encontrado") }

        val notificacion = request.toEntity(cuentaUsuario)
        val guardada = notificacionRepository.save(notificacion)

        pushNotificacionService.enviarPush(guardada.titulo, guardada.mensaje, cuentaUsuario)


        return guardada.toResponse()
    }

    @Transactional
    override fun createScheduledNotification(
        requestNotificacion: NotificacionRequest,
        requestProgramada: NotificacionProgramadaRequest
    ): NotificacionResponse {
        val cuentaUsuario = cuentaUsuarioRepository.findByPublicId(requestNotificacion.cuentaUsuarioPublicId)
            .orElseThrow { IllegalArgumentException("CuentaUsuario no encontrado - CreacionSchedule") }

        val plantilla = requestNotificacion.toEntity(cuentaUsuario)
        val guardada = notificacionRepository.save(plantilla)

        val programada = requestProgramada.toEntity(guardada)
        notificacionProgramadaRepository.save(programada)

        return guardada.toResponse()

    }

    @Transactional
    override fun processScheduledNotifications() {
        val ahora = LocalDateTime.now()
        val pendientes = notificacionProgramadaRepository.findAllByProxFechaBeforeAndProgramacionActivaIsTrue(ahora)

        pendientes.forEach { prog ->
            val plantilla = prog.notificacion
            val cuentaUsuario = plantilla.cuentaUsuario

            val nueva = notificacionRepository.save(
                plantilla.copy(
                    id = 0,
                    publicId = UUID.randomUUID(),
                    fecha_creacion = LocalDateTime.now(),
                    notificacion_leida = false
                )
            )

            pushNotificacionService.enviarPush(nueva.titulo, nueva.mensaje, cuentaUsuario)

            prog.prox_fecha = prog.prox_fecha.plusDays(3) // por ahora fijo a 3 días
            notificacionProgramadaRepository.save(prog)

            logger.info("[Programada] Notificación generada para ${cuentaUsuario.id} desde plantilla ${plantilla.publicId}")
        }
    }

    override fun obtenerHistorialNotificaciones(cuentaUsuarioPublicId: UUID): List<NotificacionResponse> {
        val notificaciones = notificacionRepository.findAllByCuentaUsuarioPublicIdOrderByFechaCreacionDesc(cuentaUsuarioPublicId)
        return notificaciones.map { it.toResponse() }
    }


    override fun marcarNotificacionComoLeida(publicId: UUID) {
        val notificacion = notificacionRepository.findByPublicId(publicId)
            .orElseThrow { IllegalArgumentException("Notificación no encontrada para ser marcada como Leída") }

        notificacion.notificacion_leida = true
        notificacionRepository.save(notificacion)
    }



}

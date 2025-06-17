package com.crisordonez.registro.service.scheduler

import com.crisordonez.registro.service.NotificacionServiceInterface
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NotificationScheduler(
    private val notificacionService: NotificacionServiceInterface
) {

    private val logger = LoggerFactory.getLogger(NotificationScheduler::class.java)

    /**
     * Ejecuta la revisión de notificaciones programadas cada minuto
     */
    @Scheduled(fixedRate = 60000)
    fun revisarNotificacionesProgramadas() {
        logger.info("Ejecutando revisión de notificaciones programadas...")
        notificacionService.processScheduledNotifications()
    }
}

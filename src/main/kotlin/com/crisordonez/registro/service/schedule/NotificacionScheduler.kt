package com.crisordonez.registro.service.schedule

import com.crisordonez.registro.service.NotificacionService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NotificacionScheduler(
    private val notificacionService: NotificacionService
) {
    private val log = LoggerFactory.getLogger(NotificacionScheduler::class.java)


    @Scheduled(cron = "0 0,30 * * * *") // Cada hora en el minuto 0 y minuto 30
    fun ejecutarNotificacionesProgramadas() {
        log.info("Ejecutando tareas programadas de notificaciones...")
        notificacionService.procesarNotificacionesProgramadas()
        log.info("Ejecución de tareas programadas finalizada.")
    }




}
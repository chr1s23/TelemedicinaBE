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


   // @Scheduled(cron = "0 0,30 * * * *") // Cada hora en el minuto 0 y minuto 30
    @Scheduled(fixedRate = 10000) // cada 30 segundos
    fun ejecutarNotificacionesProgramadas() {
        log.info("1. Ejecutando tareas programadas de notificaciones...")
        notificacionService.procesarNotificacionesProgramadas()
        log.info("2. Ejecución de tareas programadas finalizada.")
    }




}
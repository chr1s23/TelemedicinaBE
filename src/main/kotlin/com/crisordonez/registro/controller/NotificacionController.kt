package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.NotificacionProgramadaRequest
import com.crisordonez.registro.model.requests.NotificacionRequest
import com.crisordonez.registro.model.responses.NotificacionResponse
import com.crisordonez.registro.service.NotificacionServiceInterface
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/notificaciones")
class NotificationController(
    private val notificacionService: NotificacionServiceInterface
) {

    /**
     * Crea una notificación puntual (no programada).
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crearNotificacion(@Valid @RequestBody request: NotificacionRequest): NotificacionResponse {
        return notificacionService.createNotification(request)
    }
    /**
     * Crea una notificación programada (solo pruebas internas)
     */
    @PostMapping("/programadas")
    @ResponseStatus(HttpStatus.CREATED)
    fun crearNotificacionProgramada(
        @Valid @RequestBody requestNotificacion: NotificacionRequest,
        @Valid @RequestBody requestProgramada: NotificacionProgramadaRequest
    ): NotificacionResponse {
        return notificacionService.createScheduledNotification(
            requestNotificacion = requestNotificacion,
            requestProgramada = requestProgramada
        )
    }


    /**
     * Historial de notificaciones de un paciente usando su publicId
     */
    @GetMapping("/{cuentaUsuarioPublicId}")
    fun obtenerHistorial(@PathVariable cuentaUsuarioPublicId: UUID): List<NotificacionResponse> {
        return notificacionService.obtenerHistorialNotificaciones(cuentaUsuarioPublicId)
    }

    @PutMapping("/{notificacionPublicId}/marcar-leida")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun marcarComoLeida(@PathVariable notificacionPublicId: UUID) {
        notificacionService.marcarNotificacionComoLeida(notificacionPublicId)
    }


}
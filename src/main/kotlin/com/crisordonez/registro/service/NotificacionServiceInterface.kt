package com.crisordonez.registro.service

import com.crisordonez.registro.model.requests.NotificacionRequest
import com.crisordonez.registro.model.requests.NotificacionProgramadaRequest
import com.crisordonez.registro.model.responses.NotificacionResponse
import java.util.UUID

interface NotificacionServiceInterface {
    fun createNotification(request: NotificacionRequest): NotificacionResponse
    fun createScheduledNotification(
        requestNotificacion: NotificacionRequest,
        requestProgramada: NotificacionProgramadaRequest
    ): NotificacionResponse
    fun processScheduledNotifications()
    fun obtenerHistorialNotificaciones(cuentaUsuarioPublicId: UUID): List<NotificacionResponse>
    fun marcarNotificacionComoLeida(publicId: UUID)

}

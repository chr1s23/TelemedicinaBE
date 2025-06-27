package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import com.crisordonez.registro.model.responses.NotificacionResponse

interface PushNotificacionServiceInterface {
    fun enviarPushFCM(token: String?, notificacion: NotificacionResponse): String?
}

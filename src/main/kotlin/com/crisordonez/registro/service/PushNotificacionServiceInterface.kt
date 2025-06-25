package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity

interface PushNotificacionServiceInterface {
    fun enviarPushFCM(token: String?, titulo: String, mensaje: String): String?
}

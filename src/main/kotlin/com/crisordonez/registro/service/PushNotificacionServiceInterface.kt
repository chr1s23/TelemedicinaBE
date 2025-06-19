package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity

interface PushNotificacionServiceInterface {
    fun enviarPush(titulo: String, mensaje: String, cuentaUsuario: CuentaUsuarioEntity)
}

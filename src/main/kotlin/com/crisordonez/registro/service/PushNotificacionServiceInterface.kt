package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.PacienteEntity
interface PushNotificacionServiceInterface {
    fun enviarPush(titulo: String, mensaje: String, paciente: PacienteEntity)
}

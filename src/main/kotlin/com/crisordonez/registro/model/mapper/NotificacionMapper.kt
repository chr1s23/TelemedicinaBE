package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.NotificacionEntity
import com.crisordonez.registro.model.entities.PacienteEntity
import com.crisordonez.registro.model.requests.NotificacionRequest
import com.crisordonez.registro.model.responses.NotificacionResponse

object NotificacionMapper {

    fun NotificacionRequest.toEntity(paciente: PacienteEntity): NotificacionEntity {
        return NotificacionEntity(
            paciente = paciente,
            tipo_notificacion = this.tipoNotificacion,
            titulo = this.titulo,
            mensaje = this.mensaje,
            tipo_accion = this.tipoAccion,
            accion = this.accion
        )
    }

    fun NotificacionEntity.toResponse(): NotificacionResponse {
        return NotificacionResponse(
            publicId = this.publicId,
            tipoNotificacion = this.tipo_notificacion,
            titulo = this.titulo,
            mensaje = this.mensaje,
            tipoAccion = this.tipo_accion,
            accion = this.accion,
            fechaCreacion = this.fecha_creacion,
            leida = this.notificacion_leida
        )
    }
}

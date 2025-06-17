package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.NotificacionEntity
import com.crisordonez.registro.model.entities.NotificacionProgramadaEntity
import com.crisordonez.registro.model.requests.NotificacionProgramadaRequest
import com.crisordonez.registro.model.responses.NotificacionProgramadaResponse

object NotificacionProgramadaMapper {

    fun NotificacionProgramadaRequest.toEntity(base: NotificacionEntity): NotificacionProgramadaEntity {
        return NotificacionProgramadaEntity(
            notificacion = base,
            cronExpression = this.cronExpression,
            prox_fecha = this.proxFecha,
            programacion_activa = true,
            limite_fecha = this.limiteFecha
        )
    }

    fun NotificacionProgramadaEntity.toResponse(): NotificacionProgramadaResponse {
        return NotificacionProgramadaResponse(
            publicId = this.publicId,
            notificacionPublicId = this.notificacion.publicId,
            cronExpression = this.cronExpression,
            proxFecha = this.prox_fecha,
            activa = this.programacion_activa,
            limiteFecha = this.limite_fecha
        )
    }
}

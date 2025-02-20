package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.PacienteEntity
import com.crisordonez.registro.model.entities.SaludSexualEntity
import com.crisordonez.registro.model.entities.SesionChatEntity
import com.crisordonez.registro.model.mapper.SaludSexualMapper.toResponse
import com.crisordonez.registro.model.requests.SesionChatRequest
import com.crisordonez.registro.model.responses.SesionChatResponse

object SesionChatMapper {

    fun SesionChatRequest.toEntity(
        paciente: PacienteEntity, saludSexual: SaludSexualEntity
    ): SesionChatEntity {
        return SesionChatEntity(
            inicio = this.inicio,
            fin = this.fin,
            contenido = this.contenido,
            paciente = paciente,
            saludSexual = saludSexual
        )
    }

    fun SesionChatEntity.toResponse(): SesionChatResponse {
        return SesionChatResponse(
            publicId = this.publicId,
            contenido = this.contenido,
            saludSexual = this.saludSexual.toResponse()
        )
    }
}
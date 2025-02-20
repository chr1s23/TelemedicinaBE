package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.PruebaEntity
import com.crisordonez.registro.model.entities.SesionChatEntity
import com.crisordonez.registro.model.requests.PruebaRequest
import com.crisordonez.registro.model.responses.PruebaResponse
import java.text.SimpleDateFormat

object PruebaMapper {

    fun PruebaRequest.toEntity(sesion: SesionChatEntity): PruebaEntity {
        return PruebaEntity(
            fecha = SimpleDateFormat("dd/MM/yyyy").parse(this.fecha),
            dispositivo = this.dispositivo,
            sesionChat = sesion
        )
    }

    fun PruebaEntity.toResponse(): PruebaResponse {
        return PruebaResponse(
            publicId = this.publicId,
            fecha = SimpleDateFormat("dd/MM/yyyy").format(this.fecha),
            fechaResultado = this.fechaResultado?.let { SimpleDateFormat("dd/MM/yyyy").format(it) },
            dispositivo = this.dispositivo
        )
    }

    fun PruebaEntity.toUpdateFechaResultado(fechaResultado: String): PruebaEntity {
        this.fechaResultado = SimpleDateFormat("dd/MM/yyyy").parse(fechaResultado)
        return this
    }
}
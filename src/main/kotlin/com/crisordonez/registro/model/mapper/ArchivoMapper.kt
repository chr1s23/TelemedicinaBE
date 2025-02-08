package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.ArchivoEntity
import com.crisordonez.registro.model.requests.ArchivoRequest
import com.crisordonez.registro.model.responses.ArchivoResponse

object ArchivoMapper {

    fun ArchivoRequest.toEntity(): ArchivoEntity {
        return ArchivoEntity(
            nombre = this.nombre,
            tipo = this.tipo,
            contenido = this.contenido,
            tamano = this.contenido.size.toLong()
        )
    }

    fun ArchivoEntity.toResponse(): ArchivoResponse {
        return ArchivoResponse(
            publicId = this.publicId,
            nombre = this.nombre,
            tipo = this.tipo.name,
            tamano = this.tamano,
            contenido = this.contenido
        )
    }

}
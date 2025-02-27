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

    fun ArchivoRequest.toEntityUpdated(archivo: ArchivoEntity): ArchivoEntity {
        archivo.tipo = this.tipo
        archivo.nombre = this.nombre
        archivo.contenido = this.contenido
        archivo.tamano = this.contenido.size.toLong()

        return archivo
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
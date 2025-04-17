package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.EvolucionEntity
import com.crisordonez.registro.model.entities.ExamenVphEntity
import com.crisordonez.registro.model.entities.SaludSexualEntity
import com.crisordonez.registro.model.entities.SesionChatEntity
import com.crisordonez.registro.model.mapper.EvolucionMapper.toResponse
import com.crisordonez.registro.model.mapper.SaludSexualMapper.toResponse
import com.crisordonez.registro.model.requests.ExamenVphRequest
import com.crisordonez.registro.model.requests.ExamenResultadoRequest
import com.crisordonez.registro.model.responses.ExamenVphResponse
import org.springframework.web.multipart.MultipartFile
import java.text.SimpleDateFormat

object ExamenVphMapper {

    fun ExamenVphRequest.toEntity(sesion: SesionChatEntity, saludSexual: SaludSexualEntity): ExamenVphEntity {
        return ExamenVphEntity(
            fechaExamen = this.fecha,
            dispositivo = this.dispositivo,
            sesionChat = sesion,
            saludSexual = saludSexual
        )
    }

    fun ExamenVphRequest.toEntityUpdated(examen: ExamenVphEntity): ExamenVphEntity {
        examen.fechaExamen = this.fecha
        examen.dispositivo = this.dispositivo
        return examen
    }

    fun ExamenVphEntity.toResponse(): ExamenVphResponse {
        return ExamenVphResponse(
            publicId = this.publicId,
            fechaExamen = SimpleDateFormat("dd/MM/yyyy").format(this.fechaExamen),
            fechaResultado = this.fechaResultado?.let { SimpleDateFormat("dd/MM/yyyy").format(it) },
            dispositivo = this.dispositivo,
            saludSexual = this.saludSexual.toResponse(),
            evolucion = this.evolucion.map { it.toResponse() },
            tipo = this.tipo,
            contenido = this.contenido,
            tamano = this.tamano,
            nombre = this.nombre
        )
    }

    fun ExamenVphEntity.toUpdateResultado(resultado: ExamenResultadoRequest, evolucion: EvolucionEntity?, archivo: MultipartFile): ExamenVphEntity {
        this.fechaResultado = resultado.fechaResultado

        this.tipo = archivo.contentType ?: "unknown"
        this.contenido = archivo.bytes
        this.nombre = archivo.originalFilename ?: "unknown"
        this.tamano = archivo.size

        if (evolucion != null) {
            this.evolucion.add(evolucion)
        }
        return this
    }
}
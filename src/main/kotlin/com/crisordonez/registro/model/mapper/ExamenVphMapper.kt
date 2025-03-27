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
import java.text.SimpleDateFormat

object ExamenVphMapper {

    fun ExamenVphRequest.toEntity(sesion: SesionChatEntity, saludSexual: SaludSexualEntity): ExamenVphEntity {
        return ExamenVphEntity(
            fechaExamen = SimpleDateFormat("dd/MM/yyyy").parse(this.fecha),
            dispositivo = this.dispositivo,
            sesionChat = sesion,
            saludSexual = saludSexual
        )
    }

    fun ExamenVphRequest.toEntityUpdated(examen: ExamenVphEntity): ExamenVphEntity {
        examen.fechaExamen = SimpleDateFormat("dd/MM/yyyy").parse(this.fecha)
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
            tipo = this.tipo?.name,
            contenido = this.contenido,
            tamano = this.tamano,
            nombre = this.nombre
        )
    }

    fun ExamenVphEntity.toUpdateResultado(resultado: ExamenResultadoRequest, evolucion: EvolucionEntity?): ExamenVphEntity {
        this.fechaResultado = SimpleDateFormat("dd/MM/yyyy").parse(resultado.fechaResultado)
        if (resultado.archivo != null) {
            this.tipo = resultado.archivo.tipo
            this.contenido = resultado.archivo.contenido
            this.nombre = resultado.archivo.nombre
            this.tamano = resultado.archivo.contenido.size.toLong()
        }
        if (evolucion != null) {
            this.evolucion.add(evolucion)
        }
        return this
    }
}
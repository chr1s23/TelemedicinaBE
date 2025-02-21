package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.ArchivoEntity
import com.crisordonez.registro.model.entities.EvolucionEntity
import com.crisordonez.registro.model.entities.PruebaEntity
import com.crisordonez.registro.model.entities.SesionChatEntity
import com.crisordonez.registro.model.mapper.ArchivoMapper.toResponse
import com.crisordonez.registro.model.requests.PruebaRequest
import com.crisordonez.registro.model.requests.PruebaResultadoRequest
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
            dispositivo = this.dispositivo,
            archivos = this.archivos.map { it.toResponse() }
        )
    }

    fun PruebaEntity.toUpdateResultado(resultado: PruebaResultadoRequest, archivo: ArchivoEntity?, evolucion: EvolucionEntity?): PruebaEntity {
        this.fechaResultado = SimpleDateFormat("dd/MM/yyyy").parse(resultado.fechaResultado)
        if (archivo != null) {
            this.archivos.add(archivo)
        }
        if (evolucion != null) {
            this.evolucion.add(evolucion)
        }
        return this
    }
}
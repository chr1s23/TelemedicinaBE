package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.InformacionSocioeconomicaEntity
import com.crisordonez.registro.model.requests.InformacionSocioeconomicaRequest
import com.crisordonez.registro.model.responses.InformacionSocioeconomicaResponse

object InformacionSocioeconomicaMapper {

    fun InformacionSocioeconomicaRequest.toEntity(): InformacionSocioeconomicaEntity {
        return InformacionSocioeconomicaEntity(
            instruccion = this.instruccion,
            ingresos = this.ingresos,
            ocupacion = this.ocupacion,
            trabajoRemunerado = this.trabajoRemunerado,
            recibeBono = this.recibeBono
        )
    }

    fun InformacionSocioeconomicaRequest.toEntityUpdated(info: InformacionSocioeconomicaEntity): InformacionSocioeconomicaEntity {
        info.instruccion = this.instruccion
        info.ingresos = this.ingresos
        info.ocupacion = this.ocupacion
        info.trabajoRemunerado = this.trabajoRemunerado
        info.recibeBono = this.recibeBono

        return info
    }

    fun InformacionSocioeconomicaEntity.toResponse(): InformacionSocioeconomicaResponse {
        return InformacionSocioeconomicaResponse(
            publicId = this.publicId,
            instruccion = this.instruccion?.name,
            ingresos = this.ingresos?.name,
            ocupacion = this.ocupacion,
            trabajoRemunerado = this.trabajoRemunerado?.name,
            recibeBono = this.recibeBono?.name
        )
    }
}
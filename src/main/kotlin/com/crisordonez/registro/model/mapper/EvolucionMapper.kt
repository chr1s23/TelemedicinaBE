package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.EvolucionEntity
import com.crisordonez.registro.model.entities.PruebaEntity
import com.crisordonez.registro.model.mapper.PruebaMapper.toResponse
import com.crisordonez.registro.model.requests.EvolucionRequest
import com.crisordonez.registro.model.responses.EvolucionResponse

object EvolucionMapper {

    fun EvolucionRequest.toEntity(prueba: PruebaEntity): EvolucionEntity {
        return EvolucionEntity(
            temperatura = this.temperatura,
            pulso = this.pulso,
            talla = this.talla,
            peso = this.peso,
            prueba = prueba
        )
    }

    fun EvolucionEntity.toResponse(): EvolucionResponse {
        return EvolucionResponse(
            publicId = this.publicId,
            temperatura = this.temperatura,
            pulso = this.pulso,
            talla = this.talla,
            peso = this.peso,
            prueba = this.prueba.toResponse()
        )
    }
}
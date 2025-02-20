package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.SaludSexualEntity
import com.crisordonez.registro.model.requests.SaludSexualRequest
import com.crisordonez.registro.model.responses.SaludSexualResponse
import java.text.SimpleDateFormat

object SaludSexualMapper {

    fun SaludSexualRequest.toEntity(): SaludSexualEntity {
        return SaludSexualEntity(
            estaEmbarazada = this.estaEmbarazada,
            fechaUltimaMenstruacion = SimpleDateFormat("dd/MM/yyyy").parse(this.fechaUltimaMenstruacion),
            ultimoExamenPap = this.ultimoExamenPap,
            tiempoPruebaVph = this.tiempoPruebaVph,
            numParejasSexuales = this.numParejasSexuales,
            tieneEts = this.tieneEts,
            nombreEts = this.nombreEts,
            enfermedadAutoinmune = this.enfermedadAutoinmune,
            nombreAutoinmune = this.nombreAutoinmune
        )
    }

    fun SaludSexualEntity.toResponse(): SaludSexualResponse {
        return SaludSexualResponse(
            publicId = this.publicId,
            estaEmbarazada = this.estaEmbarazada,
            fechaUltimaMenstruacion = SimpleDateFormat("dd/MM/yyyy").format(this.fechaUltimaMenstruacion),
            ultimoExamenPap = this.ultimoExamenPap.name,
            tiempoPruebaVph = this.tiempoPruebaVph.name,
            numParejasSexuales = this.numParejasSexuales,
            tieneEts = this.tieneEts.name,
            nombreEts = this.nombreEts,
            enfermedadAutoinmune = this.enfermedadAutoinmune.name,
            nombreAutoinmune = this.nombreAutoinmune
        )
    }
}
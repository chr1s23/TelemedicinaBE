package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.MedicoEntity
import com.crisordonez.registro.model.mapper.EvolucionMapper.toResponse
import com.crisordonez.registro.model.requests.MedicoRequest
import com.crisordonez.registro.model.responses.MedicoResponse

object MedicoMapper {

    fun MedicoRequest.toEntity(): MedicoEntity {
        return MedicoEntity(
            nombre = this.nombre,
            correo = this.correo,
            especializacion = this.especializacion,
            sexo = this.sexo
        )
    }

    fun MedicoEntity.toEntityUpdated(medico: MedicoRequest): MedicoEntity {
        this.nombre = medico.nombre
        this.correo = medico.correo
        this.sexo = medico.sexo
        this.especializacion = medico.especializacion

        return this
    }

    fun MedicoEntity.toResponse(): MedicoResponse {
        return MedicoResponse(
            publicId = this.publicId,
            nombre = this.nombre,
            correo = this.correo,
            especializacion = this.especializacion,
            sexo = this.sexo,
            evoliciones = this.evoluciones.map { it.toResponse() }
        )
    }
}
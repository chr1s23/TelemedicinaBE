package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import com.crisordonez.registro.model.entities.PacienteEntity
import com.crisordonez.registro.model.mapper.AnamnesisMapper.toResponse
import com.crisordonez.registro.model.mapper.InformacionSocioeconomicaMapper.toResponse
import com.crisordonez.registro.model.requests.PacienteRequest
import com.crisordonez.registro.model.responses.PacienteResponse
import java.text.SimpleDateFormat

object PacienteMapper {

    fun PacienteRequest.toEntity(cuenta: CuentaUsuarioEntity): PacienteEntity {
        return PacienteEntity(
            nombre = this.nombre,
            fechaNacimiento = SimpleDateFormat("dd/MM/yyy").parse(this.fechaNacimiento),
            pais = this.pais,
            idioma = this.idioma,
            estadoCivil = this.estadoCivil,
            sexo = this.sexo,
            telefono = this.telefono,
            identificacion = this.identificacion,
            cuenta = cuenta
        )
    }

    fun PacienteEntity.toResponse(): PacienteResponse {
        return PacienteResponse(
            publicId = this.publicId,
            nombre = this.nombre,
            fechaNacimiento = SimpleDateFormat("dd/MM/yyy").format(this.fechaNacimiento),
            pais = this.pais.name,
            idioma = this.idioma.name,
            estadoCivil = this.estadoCivil.name,
            sexo = this.sexo.name,
            telefono = this.telefono,
            identificacion = this.identificacion,
            informacionSocioeconomica = this.informacionSocioeconomica?.toResponse(),
            anamnesis = this.anamnesis?.toResponse()
        )
    }
}
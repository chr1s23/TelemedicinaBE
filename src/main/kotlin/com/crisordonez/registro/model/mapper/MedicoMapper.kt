package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.MedicoEntity
import com.crisordonez.registro.model.requests.MedicoRequest
import com.crisordonez.registro.model.responses.MedicoResponse
import org.springframework.security.crypto.password.PasswordEncoder
import com.crisordonez.registro.model.mapper.EvolucionMapper.toResponse

object MedicoMapper {

    /**
     * Convierte el request en entidad, codificando la contraseña
     * y guardando sexo y número de registro.
     */
    fun MedicoRequest.toEntity(encoder: PasswordEncoder): MedicoEntity {
        return MedicoEntity(
            usuario         = this.usuario,
            contrasena      = encoder.encode(this.contrasena),
            nombre          = this.nombre,
            correo          = this.correo,
            especializacion = this.especializacion,
            sexo            = this.sexo,
            nRegistro       = this.nRegistro
        )
    }

    /**
     * Mapea la entidad a la response, incluyendo sexo, número de registro
     * (con null-safe default) y evoluciones.
     */
    fun MedicoEntity.toResponse(): MedicoResponse {
        return MedicoResponse(
            publicId        = this.publicId,
            usuario         = this.usuario,
            nombre          = this.nombre,
            correo          = this.correo,
            especializacion = this.especializacion,
            sexo            = this.sexo,
            // Si nRegistro es null en la entidad, devolvemos cadena vacía
            nRegistro       = this.nRegistro ?: "",
            // Es necesario importar EvolucionMapper.toResponse
            evoluciones     = this.evoluciones.map { it.toResponse() }
        )
    }
}

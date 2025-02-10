package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import com.crisordonez.registro.model.requests.CuentaUsuarioRequest
import com.crisordonez.registro.model.responses.CuentaUsuarioResponse
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime

object CuentaUsuarioMapper {

    fun CuentaUsuarioRequest.toEntity(): CuentaUsuarioEntity {
        return CuentaUsuarioEntity(
            nombreUsuario = this.nombreUsuario,
            correo = this.correo ?: "",
            contrasena = BCrypt.hashpw(this.contrasena, BCrypt.gensalt()),
            aceptaConsentimiento = this.aceptaConsentimiento
        )
    }

    fun CuentaUsuarioEntity.toResponse(): CuentaUsuarioResponse {
        return CuentaUsuarioResponse(
            publicId = this.publicId,
            nombreUsuario = this.nombreUsuario,
            correo = this.correo,
            aceptaConsentimiento = this.aceptaConsentimiento
        )
    }

    fun CuentaUsuarioEntity.toUpdateUltimaSesion(): CuentaUsuarioEntity {
        this.ultimaSesion = LocalDateTime.now()
        return this
    }
}
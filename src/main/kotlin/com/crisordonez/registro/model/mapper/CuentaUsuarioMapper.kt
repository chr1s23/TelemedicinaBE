package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import com.crisordonez.registro.model.requests.CuentaUsuarioRequest
import com.crisordonez.registro.model.responses.CuentaUsuarioResponse
import java.time.LocalDateTime

object CuentaUsuarioMapper {

    fun CuentaUsuarioRequest.toEntity(password: String): CuentaUsuarioEntity {
        return CuentaUsuarioEntity(
            nombreUsuario = this.nombreUsuario,
            correo = this.correo ?: "",
            contrasena = password,
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
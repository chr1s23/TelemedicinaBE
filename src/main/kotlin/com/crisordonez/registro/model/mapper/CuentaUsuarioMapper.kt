package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import com.crisordonez.registro.model.requests.CuentaUsuarioRequest
import com.crisordonez.registro.model.responses.CuentaUsuarioResponse
import java.time.LocalDateTime

object CuentaUsuarioMapper {

    fun CuentaUsuarioRequest.toEntity(pass: String): CuentaUsuarioEntity {
        return CuentaUsuarioEntity(
            nombreUsuario = this.nombreUsuario,
            correo = this.correo,
            contrasena = pass,
            aceptaConsentimiento = this.aceptaConsentimiento,
            rol = this.rol
        )
    }

    fun CuentaUsuarioRequest.toEntityUpdated(cuenta: CuentaUsuarioEntity): CuentaUsuarioEntity {
        cuenta.nombreUsuario = this.nombreUsuario
        cuenta.correo = this.correo
        return cuenta
    }

    fun CuentaUsuarioEntity.toResponse(): CuentaUsuarioResponse {
        return CuentaUsuarioResponse(
            publicId = this.publicId,
            nombreUsuario = this.nombreUsuario,
            correo = this.correo,
            aceptaConsentimiento = this.aceptaConsentimiento,
            rol = this.rol
        )
    }

    fun CuentaUsuarioEntity.toUpdateUltimaSesion(): CuentaUsuarioEntity {
        this.ultimaSesion = LocalDateTime.now()
        return this
    }
}
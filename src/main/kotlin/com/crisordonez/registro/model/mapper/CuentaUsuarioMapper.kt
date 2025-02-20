package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import com.crisordonez.registro.model.requests.CuentaUsuarioRequest
import com.crisordonez.registro.model.responses.CuentaUsuarioResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

object CuentaUsuarioMapper {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun CuentaUsuarioRequest.toEntity(pass: String): CuentaUsuarioEntity {
        return CuentaUsuarioEntity(
            nombreUsuario = this.nombreUsuario,
            correo = this.correo ?: "",
            contrasena = pass,
            aceptaConsentimiento = this.aceptaConsentimiento,
            rol = this.rol
        )
    }

    fun CuentaUsuarioEntity.toResponse(token: String?): CuentaUsuarioResponse {
        return CuentaUsuarioResponse(
            publicId = this.publicId,
            nombreUsuario = this.nombreUsuario,
            correo = this.correo,
            aceptaConsentimiento = this.aceptaConsentimiento,
            token = token ?: ""
        )
    }

    fun CuentaUsuarioEntity.toUpdateUltimaSesion(): CuentaUsuarioEntity {
        this.ultimaSesion = LocalDateTime.now()
        return this
    }
}
package com.crisordonez.registro.model.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CuentaUsuarioRequest(

    @field:NotNull(message = "El nombre de usuario es requerido")
    @field:NotBlank(message = "El nombre de usuario no puede estar vacío")
    val nombreUsuario: String,

    @field:NotNull(message = "La contrasena es requerida")
    @field:NotBlank(message = "La contrasena no puede estar vacía")
    val contrasena: String,

    val correo: String? = null,

    val aceptaConsentimiento: Boolean = false,

    val rol: String = "USER"

)

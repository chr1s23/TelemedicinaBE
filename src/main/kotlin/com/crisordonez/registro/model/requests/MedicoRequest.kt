package com.crisordonez.registro.model.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class MedicoRequest(

    @field:NotNull(message = "El usuario no puede ser nulo")
    @field:NotBlank(message = "El usuario no puede estar vacío")
    @field:Size(min = 3, max = 50, message = "El usuario debe tener entre 3 y 50 caracteres")
    val usuario: String,

    @field:NotNull(message = "La contraseña no puede ser nula")
    @field:NotBlank(message = "La contraseña no puede estar vacía")
    @field:Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    val contrasena: String,

    @field:NotNull(message = "El nombre no puede ser nulo")
    @field:NotBlank(message = "El nombre no puede estar vacío")
    val nombre: String,

    @field:NotNull(message = "El correo no puede ser nulo")
    @field:NotBlank(message = "El correo no puede estar vacío")
    val correo: String,

    val especializacion: String? = null
)

package com.crisordonez.registro.model.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class MedicoRequest(

    @field:NotNull(message = "El nombre no puede ser nulo")
    @field:NotBlank(message = "El nombre no puede estar vacio")
    val nombre: String,

    @field:NotNull(message = "El correo no puede ser nulo")
    @field:NotBlank(message = "El correo no puede estar vacio")
    val correo: String,

    val especializacion: String? = null
)

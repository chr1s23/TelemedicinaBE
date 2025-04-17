package com.crisordonez.registro.model.requests

import jakarta.validation.constraints.NotNull

data class MedicoRequest(

    @field:NotNull(message = "El nombre no puede ser nulo")
    val nombre: String,

    @field:NotNull(message = "El correo no puede ser nulo")
    val correo: String,

    val especializacion: String? = null
)

package com.crisordonez.registro.model.requests

import com.crisordonez.registro.model.enums.SexoEnum
import jakarta.validation.constraints.NotNull

data class MedicoRequest(

    @field:NotNull(message = "El nombre no puede ser nulo")
    val nombre: String,

    @field:NotNull(message = "El correo no puede ser nulo")
    val correo: String,

    val especializacion: String? = null,

    @field:NotNull(message = "El sexo no puede ser nulo")
    val sexo: SexoEnum
)

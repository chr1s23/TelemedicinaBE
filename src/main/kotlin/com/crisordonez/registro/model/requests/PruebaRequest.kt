package com.crisordonez.registro.model.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class PruebaRequest(

    @field:NotNull(message = "La fecha no puede ser nula")
    @field:NotBlank(message = "La fecha no puede estar vacía")
    val fecha: String,

    @field:NotNull(message = "El dispositivo relacionado no puede ser nulo")
    @field:NotBlank(message = "El dispositivo relacionado no puede estar vacío")
    val dispositivo: UUID

)

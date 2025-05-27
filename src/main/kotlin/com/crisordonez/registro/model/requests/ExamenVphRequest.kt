package com.crisordonez.registro.model.requests

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ExamenVphRequest(

    @field:NotNull(message = "La fecha no puede ser nula")
    val fecha: LocalDateTime,

    @field:NotNull(message = "El dispositivo relacionado no puede ser nulo")
    val dispositivo: String,

    @field:NotNull(message = "La informacion de salud sexual no puede ser nula")
    val saludSexual: SaludSexualRequest

)

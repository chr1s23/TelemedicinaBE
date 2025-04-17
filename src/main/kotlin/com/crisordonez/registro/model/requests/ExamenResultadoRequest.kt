package com.crisordonez.registro.model.requests

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ExamenResultadoRequest(

    @field:NotNull(message = "La fecha del resultado es obligatoria")
    val fechaResultado: LocalDateTime,

    val evolucion: EvolucionRequest? = null

)

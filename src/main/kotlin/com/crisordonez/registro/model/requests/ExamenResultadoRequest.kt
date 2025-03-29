package com.crisordonez.registro.model.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ExamenResultadoRequest(

    @field:NotNull(message = "La fecha del resultado es obligatoria")
    @field:NotBlank(message = "La fecha del resultado no debe estar vacia")
    val fechaResultado: String,

    val evolucion: EvolucionRequest? = null

)

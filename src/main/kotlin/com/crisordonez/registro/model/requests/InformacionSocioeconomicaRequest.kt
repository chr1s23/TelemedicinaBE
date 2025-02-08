package com.crisordonez.registro.model.requests

import com.crisordonez.registro.model.enums.IngresoEnum
import com.crisordonez.registro.model.enums.InstruccionEnum
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class InformacionSocioeconomicaRequest(

    @field:NotNull(message = "El campo instruccion no puede ser nulo")
    @field:NotBlank(message = "El campo instruccion no puede estar vacio")
    val instruccion: InstruccionEnum,

    @field:NotNull(message = "El campo ingresos no puede ser nulo")
    @field:NotBlank(message = "El campo ingresos no puede estar vacio")
    val ingresos: IngresoEnum,

    val trabajoRemunerado: Boolean = false,

    @field:NotNull(message = "El campo ocupacion no puede ser nulo")
    @field:NotBlank(message = "El campo ocupacion no puede estar vacio")
    val ocupacion: String,

    val recibeBono: Boolean = false

)

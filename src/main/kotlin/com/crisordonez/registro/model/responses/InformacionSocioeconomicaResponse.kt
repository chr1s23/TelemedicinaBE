package com.crisordonez.registro.model.responses

import java.util.UUID

data class InformacionSocioeconomicaResponse(

    val publicId: UUID,

    val instruccion: String,

    val ingresos: String,

    val trabajoRemunerado: Boolean,

    val ocupacion: String,

    val recibeBono: Boolean

)

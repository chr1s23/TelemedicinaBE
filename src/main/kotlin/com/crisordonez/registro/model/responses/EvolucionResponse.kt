package com.crisordonez.registro.model.responses

import java.util.UUID
import java.time.LocalDateTime

data class EvolucionResponse(

    val publicId: UUID,

    val temperatura: Double,

    val pulso: Int,

    val talla: Double,

    val peso: Double,


)







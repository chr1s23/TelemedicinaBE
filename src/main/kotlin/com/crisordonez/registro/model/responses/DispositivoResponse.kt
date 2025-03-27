package com.crisordonez.registro.model.responses

import java.time.LocalDateTime
import java.util.UUID

data class DispositivoResponse(

    val publicId: UUID,

    val dispositivo: String,

    val fechaRegistro: LocalDateTime

)

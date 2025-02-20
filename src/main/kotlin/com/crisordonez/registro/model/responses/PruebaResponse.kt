package com.crisordonez.registro.model.responses

import java.util.UUID

data class PruebaResponse(

    val publicId: UUID,

    val fecha: String,

    val fechaResultado: String? = null,

    val dispositivo: UUID,

    val archivos: List<ArchivoResponse> = emptyList()
)

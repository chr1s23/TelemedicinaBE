package com.crisordonez.registro.model.responses

import java.util.UUID

data class MedicoResponse(

    val publicId: UUID,

    val nombre: String,

    val correo: String,

    val especializacion: String? = null,

    val evoliciones: List<EvolucionResponse> = emptyList()
)

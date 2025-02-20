package com.crisordonez.registro.model.responses

import java.util.UUID

data class AnamnesisResponse(

    val publicId: UUID,

    val edadPrimerRelacionSexual: Int? = null,

    val edadPrimerPap: Int? = null

)

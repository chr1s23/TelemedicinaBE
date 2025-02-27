package com.crisordonez.registro.model.responses

import java.util.UUID

data class CuentaUsuarioResponse(

    val publicId: UUID,

    val nombreUsuario: String,

    val token: String? = null

)

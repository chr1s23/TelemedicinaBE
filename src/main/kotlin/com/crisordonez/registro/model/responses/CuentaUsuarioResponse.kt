package com.crisordonez.registro.model.responses

import java.util.UUID

data class CuentaUsuarioResponse(

    val publicId: UUID,

    val correo: String,

    val nombreUsuario: String,

    val aceptaConsentimiento: Boolean,

    val token: String

)

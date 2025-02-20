package com.crisordonez.registro.model.responses

import java.util.UUID

data class CuentaUsuarioResponse(

    val publicId: UUID,

    val correo: String? = null,

    val nombreUsuario: String,

    val aceptaConsentimiento: Boolean,

    val rol: String

)

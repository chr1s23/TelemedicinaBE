package com.crisordonez.registro.model.responses

import java.util.UUID

data class ExamenVphResponse(

    val publicId: UUID,

    val fechaExamen: String,

    val fechaResultado: String? = null,

    val dispositivo: String,

    val saludSexual: SaludSexualResponse,

    val evolucion: List<EvolucionResponse> = emptyList(),

    val tipo: String? = null,

    val tamano: Long? = null,

    val nombre: String? = null,

    val contenido: ByteArray? = null,

    val diagnostico: String? = null,  //nuevo para app web

    val genotipos: List<String> = emptyList() //nuevo para app web

)

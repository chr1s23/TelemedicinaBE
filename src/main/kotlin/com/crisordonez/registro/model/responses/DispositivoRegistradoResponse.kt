// src/main/kotlin/com/crisordonez/registro/model/responses/DispositivoRegistradoResponse.kt
package com.crisordonez.registro.model.responses

data class DispositivoRegistradoResponse(
    val dispositivo: String,
    val pacienteId: Long,
    val pacienteNombre: String
)

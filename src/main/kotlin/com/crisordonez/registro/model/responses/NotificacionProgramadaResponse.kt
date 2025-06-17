package com.crisordonez.registro.model.responses

import java.time.LocalDateTime
import java.util.UUID

/**
 * DTO de respuesta para la programación recurrente
 */
data class NotificacionProgramadaResponse(
    val publicId: UUID,
    val notificacionPublicId: UUID,
    val cronExpression: String,
    val proxFecha: LocalDateTime,
    val activa: Boolean,
    val limiteFecha: LocalDateTime?
)

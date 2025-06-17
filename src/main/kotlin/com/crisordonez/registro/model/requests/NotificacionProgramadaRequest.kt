package com.crisordonez.registro.model.requests

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.UUID

/**
 * DTO para crear una programación recurrente de notificación
 */
data class NotificacionProgramadaRequest(
    @field:NotNull(message = "El ID de la notificación base es requerido")
    val notificacionPublicId: UUID,

    @field:NotBlank(message = "La expresión cron es requerida")
    val cronExpression: String,

    @field:Future(message = "La fecha de próxima ejecución debe ser futura")
    val proxFecha: LocalDateTime,

    val limiteFecha: LocalDateTime? = null
)

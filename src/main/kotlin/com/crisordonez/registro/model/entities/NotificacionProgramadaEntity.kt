package com.crisordonez.registro.model.entities

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

/**
 * Extensión que almacena la lógica de re-ejecución de plantillas de notificación.
 * Cada registro apunta a una NotificacionEntity de tipo PROGRAMADA.
 */

@Entity
@Table(name = "notificacion_programada")
data class NotificacionProgramadaEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0, // ID autoincremental

    @Column(nullable = false, unique = true)
    val publicId: UUID = UUID.randomUUID(),
    /**
     * Relación uno a uno con la plantilla base.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notificacion_id", nullable = false, unique = true)
    val notificacion: NotificacionEntity,

    /**
     * Expresión cron o patrón de repetición
   */
    @Column(nullable = false)
    val cronExpression: String,

    /**
     * Próxima fecha y hora en que debe generarse una nueva instancia en la tabla base.
     */
    @Column(nullable = false)
    var prox_fecha: LocalDateTime,

    /**
     * Indicador para pausar o reanudar esta programación.
     */
    @Column(nullable = false)
    var programacion_activa: Boolean = true,

    /**
     * Fecha límite para ejecuciones (por ejemplo, 2 meses después de la primera).
     */
    @Column(nullable = true)
    val limite_fecha: LocalDateTime? = null


)

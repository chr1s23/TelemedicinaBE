package com.crisordonez.registro.model.entities

import jakarta.persistence.*
import jdk.jfr.Timestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "sesion_chat")
data class SesionChatEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Timestamp
    var inicio: LocalDateTime,

    @Timestamp
    var fin: LocalDateTime,

    @Column(columnDefinition = "TEXT")
    var contenido: String? = null,

    @OneToOne
    @JoinColumn(name = "salud_sexual_id")
    var saludSexual: SaludSexualEntity,

    @OneToOne
    @JoinColumn(name = "evolucion_id")
    var evolucion: EvolucionEntity? = null,

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    var paciente: PacienteEntity

) : AuditModel()

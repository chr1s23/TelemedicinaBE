package com.crisordonez.registro.model.entities

import jakarta.persistence.*
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "examen_vph")
data class ExamenVphEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var fechaExamen: Date,

    var fechaResultado: Date? = null,

    @Column(nullable = false)
    var dispositivo: String,

    @OneToOne
    @JoinColumn(name = "salud_sexual_id")
    var saludSexual: SaludSexualEntity,

    @OneToOne
    @JoinColumn(name = "sesion_chat_id")
    var sesionChat: SesionChatEntity,

    @OneToMany(fetch = FetchType.LAZY)
    var evolucion: MutableList<EvolucionEntity> = mutableListOf(),

    var tipo: String? = null,

    @Column(nullable = false, columnDefinition = "bytea")
    var contenido: ByteArray? = null,

    var tamano: Long? = null,

    var nombre: String? = null

) : AuditModel()

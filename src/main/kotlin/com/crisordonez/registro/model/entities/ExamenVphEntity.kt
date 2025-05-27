package com.crisordonez.registro.model.entities

import jakarta.persistence.*
import jdk.jfr.Timestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "examen_vph")
data class ExamenVphEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Timestamp
    var fechaExamen: LocalDateTime,

    @Timestamp
    var fechaResultado: LocalDateTime? = null,

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

    @Column(nullable = true, columnDefinition = "bytea")
    var contenido: ByteArray? = null,

    var tamano: Long? = null,

    var nombre: String? = null,

    var diagnostico: String? = null,          //  Nueva para app web

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "genotipos_vph", joinColumns = [JoinColumn(name = "examen_vph_id")])
    @Column(name = "genotipo")
    var genotipos: List<String> = emptyList()  //  Nueva para app web

) : AuditModel()

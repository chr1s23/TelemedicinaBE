package com.crisordonez.registro.model.entities

import jakarta.persistence.*
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "prueba")
data class PruebaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var fecha: Date,

    var fechaResultado: Date? = null,

    @Column(nullable = false)
    var dispositivo: UUID,

    @OneToMany(fetch = FetchType.LAZY)
    var archivos: MutableList<ArchivoEntity> = mutableListOf(),

    @OneToOne
    @JoinColumn(name = "sesion_chat_id")
    var sesionChat: SesionChatEntity,

    @OneToMany(fetch = FetchType.LAZY)
    var evolucion: MutableList<EvolucionEntity> = mutableListOf()

) : AuditModel()

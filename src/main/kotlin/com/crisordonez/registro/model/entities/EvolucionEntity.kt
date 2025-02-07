package com.crisordonez.registro.model.entities

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "evolucion")
data class EvolucionEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var temperatura: Double,

    @Column(nullable = false)
    var pulso: Int,

    @Column(nullable = false)
    var talla: Double,

    @Column(nullable = false)
    var peso: Double,

    @OneToOne
    @JoinColumn(name = "prueba_id")
    var prueba: PruebaEntity

) : AuditModel()

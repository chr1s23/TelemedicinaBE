package com.crisordonez.registro.model.entities

import com.crisordonez.registro.model.enums.SexoEnum
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "medicos")
data class MedicoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var nombre: String,

    @Column(nullable = false)
    var correo: String,

    var especializacion: String? = null,

    @Enumerated(EnumType.STRING)
    var sexo: SexoEnum,

    @OneToMany(fetch = FetchType.LAZY)
    var evoluciones: MutableList<EvolucionEntity> = mutableListOf()

) : AuditModel()

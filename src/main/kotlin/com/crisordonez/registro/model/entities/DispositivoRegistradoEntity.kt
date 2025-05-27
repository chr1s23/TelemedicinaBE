// src/main/kotlin/com/crisordonez/registro/model/entities/DispositivoRegistradoEntity.kt
package com.crisordonez.registro.model.entities

import jakarta.persistence.*


@Entity
@Table(name = "dispositivos_registrados")
data class DispositivoRegistradoEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val dispositivo: String,

    // FK numérica
    @Column(name = "paciente_id", nullable = false)
    val pacienteId: Long,

    // timestamp, publicId, etc...
    // ...

    // aquí la relación
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", insertable = false, updatable = false)
    val paciente: PacienteEntity
)



package com.crisordonez.registro.model.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "dispositivos_registrados")
data class DispositivoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var dispositivo: String,

    @Column(nullable = false)
    var fechaRegistro: LocalDateTime,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    var paciente: PacienteEntity

) : AuditModel()

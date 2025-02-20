package com.crisordonez.registro.model.entities

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "cuentas_usuario")
data class CuentaUsuarioEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Column(unique = true)
    var correo: String,

    @Column(nullable = false, unique = true)
    var nombreUsuario: String,

    @Column(nullable = false)
    var contrasena: String,

    var rol: String = "USER",

    var aceptaConsentimiento: Boolean = false,

    var ultimaSesion: LocalDateTime? = null,

    @OneToOne
    @JoinColumn(name = "paciente_id")
    var paciente: PacienteEntity? = null

) : AuditModel()

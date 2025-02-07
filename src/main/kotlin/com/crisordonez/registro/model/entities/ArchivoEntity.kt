package com.crisordonez.registro.model.entities

import com.crisordonez.registro.model.enums.TipoArchivoEnum
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "archivos")
data class ArchivoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var tipo: TipoArchivoEnum,

    @Column(nullable = false)
    var contenido: ByteArray,

    @Column(nullable = false)
    var tamano: Long,

    @Column(nullable = false)
    var nombre: String

): AuditModel()

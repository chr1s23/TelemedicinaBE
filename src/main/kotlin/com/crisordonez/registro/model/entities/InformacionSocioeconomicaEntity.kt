package com.crisordonez.registro.model.entities

import com.crisordonez.registro.model.enums.IngresoEnum
import com.crisordonez.registro.model.enums.InstruccionEnum
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "informacion_socioeconomica")
data class InformacionSocioeconomicaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var instruccion: InstruccionEnum = InstruccionEnum.PRIMARIA,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var ingresos: IngresoEnum = IngresoEnum.MENOR_450,

    @Column(nullable = false)
    var trabajoRemunerado: Boolean = false,

    @Column(nullable = false)
    var ocupacion: String,

    @Column(nullable = false)
    var recibeBono: Boolean = false

) : AuditModel()

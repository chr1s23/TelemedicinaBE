package com.crisordonez.registro.model.entities

import com.crisordonez.registro.model.enums.EstadoCivilEnum
import com.crisordonez.registro.model.enums.IdiomaEnum
import com.crisordonez.registro.model.enums.PaisEnum
import com.crisordonez.registro.model.enums.SexoEnum
import jakarta.persistence.*
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "pacientes")
data class PacienteEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var nombre: String,

    @Column(nullable = false)
    var fechaNacimiento: Date,

    @Enumerated(EnumType.STRING)
    var pais: PaisEnum = PaisEnum.ECUADOR,

    @Enumerated(EnumType.STRING)
    var lenguaMaterna: IdiomaEnum = IdiomaEnum.ESPANOL,

    @Enumerated(EnumType.STRING)
    var estadoCivil: EstadoCivilEnum = EstadoCivilEnum.CASADO,

    @Enumerated(EnumType.STRING)
    var sexo: SexoEnum = SexoEnum.FEMENINO,

    var identificacion: String? = null,

    @OneToOne
    @JoinColumn(name = "cuenta_id")
    var cuenta: CuentaUsuarioEntity,

    @OneToOne
    @JoinColumn(name = "info_socioeconomica_id")
    var informacionSocioeconomica: InformacionSocioeconomicaEntity? = null,

    @OneToOne
    @JoinColumn(name = "anamnesis_id")
    var anamnesis: AnamnesisEntity? = null,

    @OneToMany(fetch = FetchType.LAZY)
    var sesionChat: MutableList<SesionChatEntity> = mutableListOf()

) : AuditModel()

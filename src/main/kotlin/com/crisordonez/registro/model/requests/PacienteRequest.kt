package com.crisordonez.registro.model.requests

import com.crisordonez.registro.model.enums.EstadoCivilEnum
import com.crisordonez.registro.model.enums.IdiomaEnum
import com.crisordonez.registro.model.enums.PaisEnum
import com.crisordonez.registro.model.enums.SexoEnum
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PacienteRequest(

    @field:NotNull(message = "El nombre es requerido")
    @field:NotBlank(message = "El nombre no puede estar vacío")
    val nombre: String,

    @field:NotNull(message = "La fecha de nacimiento es requerida")
    @field:NotBlank(message = "La fecha de nacimiento no puede estar vacía")
    val fechaNacimiento: String,

    @field:NotNull(message = "El país es requerido")
    @field:NotBlank(message = "El país no puede estar vacío")
    val pais: PaisEnum,

    @field:NotNull(message = "El idioma es requerido")
    @field:NotBlank(message = "El idioma no puede estar vacío")
    val lenguaMaterna: IdiomaEnum,

    @field:NotNull(message = "El estado civil es requerido")
    @field:NotBlank(message = "El estado civil no puede estar vacío")
    val estadoCivil: EstadoCivilEnum,

    @field:NotNull(message = "El sexo es requerido")
    @field:NotBlank(message = "El sexo no puede estar vacío")
    val sexo: SexoEnum,

    val identificacion: String? = null,

    val infoSocioeconomica: InformacionSocioeconomicaRequest? = null

)

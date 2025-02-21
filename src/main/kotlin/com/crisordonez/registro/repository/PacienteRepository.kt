package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.PacienteEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional
import java.util.UUID

interface PacienteRepository: CrudRepository< PacienteEntity, Long> {

    fun findByCuentaPublicId(publicId: UUID): Optional<PacienteEntity>
}
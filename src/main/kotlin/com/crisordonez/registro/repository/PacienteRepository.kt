package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.PacienteEntity
import org.springframework.data.repository.CrudRepository

interface PacienteRepository: CrudRepository< PacienteEntity, Long> {
}
package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.ExamenVphEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional
import java.util.UUID

interface ExamenVphRepository: CrudRepository<ExamenVphEntity, Long> {

    fun findByDispositivo(dispositivo: String): Optional<ExamenVphEntity>

}
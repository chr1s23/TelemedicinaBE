package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.EvolucionEntity
import org.springframework.data.repository.CrudRepository

interface EvolucionRepository: CrudRepository<EvolucionEntity, Long> {
}
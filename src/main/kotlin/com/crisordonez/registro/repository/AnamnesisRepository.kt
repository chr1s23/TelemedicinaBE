package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.AnamnesisEntity
import org.springframework.data.repository.CrudRepository

interface AnamnesisRepository: CrudRepository<AnamnesisEntity, Long> {
}
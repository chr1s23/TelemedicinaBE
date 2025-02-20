package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.PruebaEntity
import org.springframework.data.repository.CrudRepository

interface PruebaRepository: CrudRepository<PruebaEntity, Long> {
}
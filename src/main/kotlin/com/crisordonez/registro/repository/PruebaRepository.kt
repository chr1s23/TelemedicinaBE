package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.PruebaEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional
import java.util.UUID

interface PruebaRepository: CrudRepository<PruebaEntity, Long> {

    fun findByDispositivo(dispositivo: UUID): Optional<PruebaEntity>

}
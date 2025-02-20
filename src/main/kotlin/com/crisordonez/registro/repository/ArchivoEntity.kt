package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.ArchivoEntity
import org.springframework.data.repository.CrudRepository

interface ArchivoEntity: CrudRepository<ArchivoEntity, Long> {
}
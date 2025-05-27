package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.DispositivoEntity
import org.springframework.data.repository.CrudRepository

interface DispositivoRepository: CrudRepository<DispositivoEntity, Long> {
}
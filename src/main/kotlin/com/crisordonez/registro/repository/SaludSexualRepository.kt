package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.SaludSexualEntity
import org.springframework.data.repository.CrudRepository

interface SaludSexualRepository: CrudRepository< SaludSexualEntity, Long> {
}
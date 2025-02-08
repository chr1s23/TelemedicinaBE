package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import org.springframework.data.repository.CrudRepository

interface CuentaUsuarioRepository: CrudRepository<CuentaUsuarioEntity, Long> {
}
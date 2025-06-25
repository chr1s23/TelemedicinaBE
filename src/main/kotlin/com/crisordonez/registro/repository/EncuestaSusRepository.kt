package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.EncuestaSusEntity
import org.springframework.data.jpa.repository.JpaRepository
import com.crisordonez.registro.model.entities.CuentaUsuarioEntity


interface EncuestaSusRepository : JpaRepository<EncuestaSusEntity, Long> {
    fun findByCuentaUsuario(cuentaUsuario: CuentaUsuarioEntity): EncuestaSusEntity?
}

package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional
import java.util.UUID

interface CuentaUsuarioRepository: CrudRepository<CuentaUsuarioEntity, Long> {

    fun findByNombreUsuario(nombreUsuario: String): Optional<CuentaUsuarioEntity>

    fun findByPublicId(publicId: UUID): Optional<CuentaUsuarioEntity>

}
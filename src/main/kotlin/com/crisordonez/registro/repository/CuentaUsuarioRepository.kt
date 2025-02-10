package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface CuentaUsuarioRepository: CrudRepository<CuentaUsuarioEntity, Long> {

    fun findByCorreoOrNombreUsuario(correo: String, nombreUsuario: String): Optional<CuentaUsuarioEntity>

    fun findByNombreUsuario(nombreUsuario: String): Optional<CuentaUsuarioEntity>

}
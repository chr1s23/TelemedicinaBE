// src/main/kotlin/com/crisordonez/registro/repository/AdministradorRepository.kt
package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.Administrador
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

/**
 * Repositorio para la entidad Administrador.
 */
interface AdministradorRepository : JpaRepository<Administrador, Long> {

    /**
     * Busca un Administrador por su publicId (UUID).
     * Devuelve null si no existe.
     */
    fun findByPublicId(publicId: UUID): Administrador?

    /**
     * Verifica si existe un Administrador con ese publicId.
     */
    fun existsByPublicId(publicId: UUID): Boolean

    /**
     * Elimina un Administrador por su publicId.
     */
    fun deleteByPublicId(publicId: UUID)

    /**
     * Busca un Administrador por su nombre de usuario.
     * Devuelve null si no existe.
     */
    fun findByUsuario(usuario: String): Administrador?
}

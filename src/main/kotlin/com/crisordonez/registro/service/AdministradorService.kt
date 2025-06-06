// src/main/kotlin/com/crisordonez/registro/service/AdministradorService.kt
package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.Administrador
import com.crisordonez.registro.repository.AdministradorRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class AdministradorService(
    private val repo: AdministradorRepository,
    private val encoder: PasswordEncoder
) {

    /**
     * Crea un nuevo Administrador con hash de contraseña.
     */
    fun create(nombre: String, usuario: String, rawPassword: String): Administrador {
        val admin = Administrador(
            nombre = nombre,
            usuario = usuario,
            contrasenaHash = encoder.encode(rawPassword)
        )
        return repo.save(admin)
    }

    /** Lista todos los administradores. */
    fun listAll(): List<Administrador> = repo.findAll()

    /**
     * Obtiene un Administrador por su publicId (UUID en String).
     */
    fun getById(id: String): Administrador? =
        repo.findByPublicId(UUID.fromString(id))

    /**
     * Actualiza un Administrador existente.
     */
    fun update(id: String, nombre: String, usuario: String, rawPassword: String): Administrador? {
        val existing = getById(id) ?: return null
        existing.apply {
            this.nombre = nombre
            this.usuario = usuario
            this.contrasenaHash = encoder.encode(rawPassword)
        }
        return repo.save(existing)
    }

    /**
     * Elimina un Administrador por publicId.
     */
    fun delete(id: String): Boolean {
        val existing = getById(id) ?: return false
        repo.delete(existing)
        return true
    }

    /**
     * Autentica y devuelve el Administrador completo si las credenciales son válidas.
     */
    fun authenticateAndGet(usuario: String, rawPassword: String): Administrador? {
        val adminEntity = repo.findByUsuario(usuario)
        if (adminEntity != null && encoder.matches(rawPassword, adminEntity.contrasenaHash)) {
            val updated = adminEntity.apply { lastLogin = OffsetDateTime.now() }
            return repo.save(updated)
        }
        return null
    }

}

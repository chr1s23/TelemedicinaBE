// src/main/kotlin/com/crisordonez/registro/service/MedicoService.kt
package com.crisordonez.registro.service

import com.crisordonez.registro.model.mapper.MedicoMapper.toEntity
import com.crisordonez.registro.model.mapper.MedicoMapper.toResponse
import com.crisordonez.registro.model.requests.MedicoRequest
import com.crisordonez.registro.model.responses.MedicoResponse
import com.crisordonez.registro.repository.MedicoRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
import java.util.UUID

@Service
class MedicoService(
    private val repo: MedicoRepository,
    private val encoder: PasswordEncoder
) {

    /**
     * Crea un nuevo médico.
     */
    fun crearMedico(dto: MedicoRequest): MedicoResponse {
        val entity = dto.toEntity(encoder)
        val saved  = repo.save(entity)
        return saved.toResponse()
    }

    /**
     * Lista todos los médicos.
     */
    fun listarMedicos(): List<MedicoResponse> =
        repo.findAll().map { it.toResponse() }

    /**
     * Obtiene un médico por su ID UUID (string).
     */
    fun obtenerMedico(id: String): MedicoResponse {
        val medico = repo.findByPublicId(UUID.fromString(id))
            ?: throw NoSuchElementException("Médico id=$id no encontrado")
        return medico.toResponse()
    }

    /**
     * Actualiza un médico existente.
     */
    fun actualizarMedico(id: String, dto: MedicoRequest): MedicoResponse {
        val medico = repo.findByPublicId(UUID.fromString(id))
            ?: throw NoSuchElementException("Médico id=$id no encontrado")
        medico.apply {
            usuario         = dto.usuario
            contrasena      = encoder.encode(dto.contrasena)
            nombre          = dto.nombre
            correo          = dto.correo
            especializacion = dto.especializacion
        }
        return repo.save(medico).toResponse()
    }

    /**
     * Elimina un médico por ID UUID (string).
     */
    fun eliminarMedico(id: String) {
        val medico = repo.findByPublicId(UUID.fromString(id))
            ?: throw NoSuchElementException("Médico id=$id no encontrado")
        repo.delete(medico)
    }

    /**
     * Autentica médico por usuario/contraseña y devuelve su DTO, o null si falla.
     */
    fun authenticateAndGet(usuario: String, rawPassword: String): MedicoResponse? {
        val medicoEntity = repo.findByUsuario(usuario)
        if (medicoEntity != null && encoder.matches(rawPassword, medicoEntity.contrasena)) {
            return medicoEntity.toResponse()
        }
        return null
    }
}

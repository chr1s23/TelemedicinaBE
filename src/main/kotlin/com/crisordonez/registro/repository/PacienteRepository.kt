// src/main/kotlin/com/crisordonez/registro/repository/PacienteRepository.kt
package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.PacienteEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

/**
 * Repositorio de pacientes, con búsquedas por publicId y operaciones auxiliares.
 */
interface PacienteRepository : JpaRepository<PacienteEntity, Long> {

    // Busca por el publicId de la propia entidad PacienteEntity
    fun findByPublicId(publicId: UUID): Optional<PacienteEntity>
    fun existsByPublicId(publicId: UUID): Boolean
    fun deleteByPublicId(publicId: UUID)

    // <-- Este es el que necesitas para crear la anamnesis:
    // Busca el Paciente cuyo campo `cuenta.publicId` (relación a tu tabla de usuarios) coincida
    fun findByCuentaPublicId(cuentaPublicId: UUID): Optional<PacienteEntity>
}

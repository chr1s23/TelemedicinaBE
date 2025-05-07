package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.MedicoEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional
import java.util.UUID

interface MedicoRepository: CrudRepository<MedicoEntity, Long> {

    fun findByPublicId(publicId: UUID): Optional<MedicoEntity>

    fun findByNombre(nombre: String): Optional<MedicoEntity>

    fun findByCorreo(correo: String): Optional<MedicoEntity>

}
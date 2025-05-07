package com.crisordonez.registro.service

import com.crisordonez.registro.model.errors.ConflictException
import com.crisordonez.registro.model.errors.NotFoundException
import com.crisordonez.registro.model.mapper.MedicoMapper.toEntity
import com.crisordonez.registro.model.mapper.MedicoMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.MedicoMapper.toResponse
import com.crisordonez.registro.model.requests.MedicoRequest
import com.crisordonez.registro.model.responses.MedicoResponse
import com.crisordonez.registro.repository.MedicoRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MedicoService: MedicoServiceInterface {

    @Autowired
    lateinit var medicoRepository: MedicoRepository

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun createMedico(medico: MedicoRequest) {
        log.info("Creando medico - Nombre: ${medico.nombre}")
        val medicoNombre = medicoRepository.findByNombre(medico.nombre)

        if (medicoNombre.isPresent) {
            throw ConflictException("El nombre del médico ya está registrado")
        }

        val medicoCorreo = medicoRepository.findByCorreo(medico.correo)

        if (medicoCorreo.isPresent) {
            throw ConflictException("El correo ya está registrado")
        }

        medicoRepository.save(medico.toEntity())
        log.info("Medico creado correctamente")
    }

    override fun getMedico(publicId: UUID): MedicoResponse {
        log.info("Consultando medico - PublicId: $publicId")

        val medicoEntity = medicoRepository.findByPublicId(publicId).orElseThrow {
            throw NotFoundException("No se encontró el médico solicitado")
        }

        log.info("Médico consultado correctamente")
        return medicoEntity.toResponse()
    }

    override fun getAllMedicos(): List<MedicoResponse> {
        log.info("Consultado todos los medicos registrados")

        val medicos = medicoRepository.findAll().map { it.toResponse() }

        log.info("Medicos consultados correctamente - Total: ${medicos.size}")
        return medicos
    }

    override fun updateMedico(publicId: UUID, medico: MedicoRequest) {
        log.info("Actualizando medico - PublicId: $publicId")

        val medicoEntity = medicoRepository.findByPublicId(publicId).orElseThrow {
            throw NotFoundException("No se encontró el médico solicitado")
        }

        val medicoExistente = medicoRepository.findByNombre(medico.nombre)

        if (medicoExistente.isPresent && medicoExistente.get().publicId != publicId) {
            throw ConflictException("El nombre del medico ya está registrado")
        }

        val medicoCorreo = medicoRepository.findByCorreo(medico.correo)

        if (medicoCorreo.isPresent && medicoCorreo.get().publicId != publicId) {
            throw ConflictException("El correo ya está registrado")
        }

        medicoRepository.save(medicoEntity.toEntityUpdated(medico))

        log.info("Médico actualizado correctamente")
    }

    override fun deleteMedico(publicId: UUID) {
        log.info("Eliminando medico - PublicId: $publicId")

        val medicoEntity = medicoRepository.findByPublicId(publicId).orElseThrow {
            throw NotFoundException("No se encontró el médico solicitado")
        }

        medicoRepository.delete(medicoEntity)

        log.info("Médico eliminado correctamente")
    }
}
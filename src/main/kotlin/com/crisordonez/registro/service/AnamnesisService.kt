package com.crisordonez.registro.service

import com.crisordonez.registro.model.mapper.AnamnesisMapper.toEntity
import com.crisordonez.registro.model.mapper.AnamnesisMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.AnamnesisMapper.toResponse
import com.crisordonez.registro.model.requests.AnamnesisRequest
import com.crisordonez.registro.model.responses.AnamnesisResponse
import com.crisordonez.registro.repository.AnamnesisRepository
import com.crisordonez.registro.repository.PacienteRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AnamnesisService(

    @Autowired private val anamnesisRepository: AnamnesisRepository,
    @Autowired private val pacienteRepository: PacienteRepository
) : AnamnesisServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)



    override fun crearAnamnesis(publicId: UUID, anamnesis: AnamnesisRequest) {
        try {
            log.info("Creando anamnesis")
            val paciente = pacienteRepository.findByCuentaPublicId(publicId).orElseThrow {
                throw Exception("No existe la cuenta de usuario solicitada")
            }
            val anamnesisEntity = anamnesisRepository.save(anamnesis.toEntity(paciente))
            paciente.anamnesis = anamnesisEntity
            pacienteRepository.save(paciente)
            log.info("Anamnesis creada correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getAnamnesis(publicId: UUID): AnamnesisResponse {
        try {
            log.info("Consultando registro de anamnesis - PublicId: $publicId")
            val registro = anamnesisRepository.findByPublicId(publicId).orElseThrow {
                throw Exception("No existe el registro solicitado")
            }
            log.info("Registro consultado correctamente")
            return registro.toResponse()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getTodasAnamnesis(): List<AnamnesisResponse> {
        try {
            log.info("Consultando todos los registros de anamnesis")
            val registros = anamnesisRepository.findAll().map { it.toResponse() }
            log.info("Registros consultados - Total: ${registros.size}")
            return registros
        } catch (e: Exception) {
            throw e
        }
    }

    override fun editarAnamnesis(publicId: UUID, anamnesis: AnamnesisRequest) {
        try {
            log.info("Editanto registro de anamnesis - PublicId: $publicId")
            val registro = anamnesisRepository.findByPublicId(publicId).orElseThrow {
                throw Exception("No existe el registro de anamnesis solicitado")
            }
            anamnesisRepository.save(anamnesis.toEntityUpdated(registro))
            log.info("Registro actualizado correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun eliminarAnamnesis(publicId: UUID) {
        try {
            log.info("Eliminando registro de anamnesis - PublicId: $publicId")
            val registro = anamnesisRepository.findByPublicId(publicId).orElseThrow {
                throw Exception("No existe el registro requerido")
            }
            anamnesisRepository.delete(registro)
            log.info("Registro de anamnesis eliminado correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

}

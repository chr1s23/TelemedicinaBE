package com.crisordonez.registro.service

import com.crisordonez.registro.model.mapper.PacienteMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.PacienteMapper.toResponse
import com.crisordonez.registro.model.requests.PacienteRequest
import com.crisordonez.registro.model.responses.PacienteResponse
import com.crisordonez.registro.repository.PacienteRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PacienteService(
    @Autowired private val pacienteRepository: PacienteRepository
): PacienteServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun editarPaciente(publicId: UUID, paciente: PacienteRequest) {
        try {
            log.info("Editando informacion paciente - PublicId: $publicId")
            val pacienteExistente = pacienteRepository.findByCuentaPublicId(publicId).orElseThrow {
                throw Exception("No existe la informacion del paciente solicitado")
            }

            pacienteRepository.save(paciente.toEntityUpdated(pacienteExistente))
            log.info("Informacion del paciente editada correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getPaciente(publicId: UUID): PacienteResponse {
        try {
            log.info("Consultando informacion paciente - PublicId: $publicId")
            val paciente = pacienteRepository.findByCuentaPublicId(publicId).orElseThrow {
                throw Exception("No existe la informacion del paciente solicitado")
            }

            log.info("Informacion consultada correctamente")
            return paciente.toResponse()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getTodosPacientes(): List<PacienteResponse> {
        try {
            log.info("Consultando informacion de los pacientes")
            val pacientes = pacienteRepository.findAll().map { it.toResponse() }
            log.info("Informacion consultada correctamente - Total: ${pacientes.size} registros")
            return pacientes
        } catch (e: Exception) {
            throw e
        }
    }
}
// src/main/kotlin/com/crisordonez/registro/service/PacienteService.kt
package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.PacienteEntity
import com.crisordonez.registro.model.mapper.PacienteMapper.toEntity
import com.crisordonez.registro.model.mapper.PacienteMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.PacienteMapper.toResponse
import com.crisordonez.registro.model.requests.PacienteRequest
import com.crisordonez.registro.model.responses.PacienteResponse
import com.crisordonez.registro.repository.DispositivoRegistradoRepository
import com.crisordonez.registro.repository.PacienteRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PacienteService(
    @Autowired private val pacienteRepository: PacienteRepository,
    @Autowired private val dispositivoRepo: DispositivoRegistradoRepository
) : PacienteServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun editarPaciente(publicId: UUID, paciente: PacienteRequest) {
        log.info("Editando paciente - publicId=$publicId")
        val existente = pacienteRepository.findByPublicId(publicId)
            .orElseThrow { Exception("Paciente no encontrado: $publicId") }
        pacienteRepository.save(paciente.toEntityUpdated(existente))
        log.info("Paciente editado correctamente")
    }

    override fun crearPaciente(paciente: PacienteRequest): PacienteEntity {
        log.info("Creando nuevo paciente")
        val entidad = paciente.toEntity()
        val guardado = pacienteRepository.save(entidad)
        log.info("Paciente creado - publicId=${guardado.publicId}")
        return guardado
    }

    override fun getPaciente(publicId: UUID): PacienteResponse {
        log.info("Consultando paciente - publicId=$publicId")
        val entidad = pacienteRepository.findByPublicId(publicId)
            .orElseThrow { Exception("Paciente no encontrado: $publicId") }
        return entidad.toResponse()
    }

    override fun getTodosPacientes(): List<PacienteResponse> {
        log.info("Consultando todos los pacientes")
        val list = pacienteRepository.findAll().map { it.toResponse() }
        log.info("Total pacientes: ${list.size}")
        return list
    }

    override fun findByDispositivo(codigo: String): PacienteResponse? {
        log.info("Buscando paciente por dispositivo: $codigo")
        val disp = dispositivoRepo.findByDispositivo(codigo)
            .orElseThrow { Exception("Dispositivo no registrado: $codigo") }
        // aquí usamos la relación a PacienteEntity directamente
        val pacienteEnt = disp.paciente
        return pacienteEnt.toResponse()
    }
}

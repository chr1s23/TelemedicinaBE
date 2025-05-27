// src/main/kotlin/com/crisordonez/registro/service/PacienteService.kt
package com.crisordonez.registro.service


import com.crisordonez.registro.model.entities.PacienteEntity
import com.crisordonez.registro.model.mapper.PacienteMapper.toEntity
import com.crisordonez.registro.model.errors.NotFoundException
import com.crisordonez.registro.model.mapper.DispositivoMapper.toEntity
import com.crisordonez.registro.model.mapper.InformacionSocioeconomicaMapper.toEntity
import com.crisordonez.registro.model.mapper.InformacionSocioeconomicaMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.PacienteMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.PacienteMapper.toResponse
import com.crisordonez.registro.model.requests.DispositivoRequest
import com.crisordonez.registro.model.requests.PacienteRequest
import com.crisordonez.registro.model.responses.PacienteResponse
import com.crisordonez.registro.repository.DispositivoRegistradoRepository
import com.crisordonez.registro.repository.InformacionSocioeconomicaRepository
import com.crisordonez.registro.repository.PacienteRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PacienteService(
    @Autowired private val pacienteRepository: PacienteRepository,

    @Autowired private val dispositivoRepo: DispositivoRegistradoRepository
    @Autowired private val dispositivoRepository: DispositivoRepository,
    @Autowired private val informacionSocioeconomicaRepository: InformacionSocioeconomicaRepository
): PacienteServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun editarPaciente(publicId: UUID, paciente: PacienteRequest) {
        log.info("Editando informacion paciente - PublicId: $publicId")
        val pacienteExistente = pacienteRepository.findByCuentaPublicId(publicId).orElseThrow {
            throw NotFoundException("No existe la informacion del paciente solicitado")
        }

        val pacienteActualizado = paciente.toEntityUpdated(pacienteExistente)

        if (paciente.infoSocioeconomica != null) {
            val informacionEntity = if (pacienteActualizado.informacionSocioeconomica != null) {
                informacionSocioeconomicaRepository.save(paciente.infoSocioeconomica.toEntityUpdated(pacienteActualizado.informacionSocioeconomica!!))
            } else {
                informacionSocioeconomicaRepository.save(paciente.infoSocioeconomica.toEntity(pacienteActualizado))
            }
            pacienteActualizado.informacionSocioeconomica = informacionEntity
        }

        pacienteRepository.save(pacienteActualizado)
        log.info("Informacion del paciente editada correctamente")
    }

    override fun findByDispositivo(codigo: String): PacienteResponse? {
        log.info("Buscando paciente por dispositivo: $codigo")
        val disp = dispositivoRepo.findByDispositivo(codigo)
            .orElseThrow { Exception("Dispositivo no registrado: $codigo") }
        // aquí usamos la relación a PacienteEntity directamente
        val pacienteEnt = disp.paciente
        return pacienteEnt.toResponse()
    }
    
    override fun getPaciente(publicId: UUID): PacienteResponse {
        log.info("Consultando informacion paciente - PublicId: $publicId")
        val paciente = pacienteRepository.findByCuentaPublicId(publicId).orElseThrow {
            throw NotFoundException("No existe la informacion del paciente solicitado")
        }

        log.info("Informacion consultada correctamente")
        return paciente.toResponse()
    }

    override fun getTodosPacientes(): List<PacienteResponse> {
        log.info("Consultando informacion de los pacientes")
        val pacientes = pacienteRepository.findAll().map { it.toResponse() }
        log.info("Informacion consultada correctamente - Total: ${pacientes.size} registros")
        return pacientes
    }

    override fun registrarDispositivo(publicId: UUID, dispositivo: DispositivoRequest): String {
        log.info("Registrando nuevo dispositivo al paciente - PublicId: $publicId")
        val paciente = pacienteRepository.findByCuentaPublicId(publicId).orElseThrow {
            throw NotFoundException("No existe la informacion del paciente solicitado")
        }
        val dispositivoEntity = dispositivoRepository.save(dispositivo.toEntity(paciente))
        paciente.dispositivos.add(dispositivoEntity)
        pacienteRepository.save(paciente)
        log.info("Dispositivo registrado correctamente")
        return dispositivo.dispositivo
    }
}

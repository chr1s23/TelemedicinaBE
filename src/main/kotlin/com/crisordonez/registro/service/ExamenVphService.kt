package com.crisordonez.registro.service

import com.crisordonez.registro.model.errors.NotFoundException
import com.crisordonez.registro.model.mapper.EvolucionMapper.toEntity
import com.crisordonez.registro.model.mapper.ExamenVphMapper.toResponse
import com.crisordonez.registro.model.mapper.ExamenVphMapper.toUpdateResultado
import com.crisordonez.registro.model.requests.ExamenResultadoRequest
import com.crisordonez.registro.model.responses.ExamenVphResponse
import com.crisordonez.registro.repository.EvolucionRepository
import com.crisordonez.registro.repository.ExamenVphRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ExamenVphService(
    @Autowired private val examenVphRepository: ExamenVphRepository,
    @Autowired private val evolucionRepository: EvolucionRepository
): ExamenVphServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun establecerResultadoPrueba(publicId: String, pruebaRequest: ExamenResultadoRequest, archivo: MultipartFile) {
        log.info("Estableciendo resultado - Prueba: $publicId")
        val prueba = examenVphRepository.findByDispositivo(publicId).orElseThrow {
            throw NotFoundException("No existe un dispositivo relacionado")
        }

        val evolucion = if (pruebaRequest.evolucion != null) {
            evolucionRepository.save(pruebaRequest.evolucion.toEntity(prueba))
        } else { null }

        examenVphRepository.save(prueba.toUpdateResultado(pruebaRequest, evolucion, archivo))
        log.info("Resultado establecido correctamente")
    }

    override fun getPrueba(publicId: String): ExamenVphResponse {
        log.info("Consultando prueba por dispositivo relacionado - Dispositivo: $publicId")
        val prueba = examenVphRepository.findByDispositivo(publicId).orElseThrow {
            throw NotFoundException("No existe prueba relacionada con el dispositivo")
        }
        log.info("Prueba consultada correctamente")
        return prueba.toResponse()
    }

    override fun getTodasPruebas(): List<ExamenVphResponse> {
        log.info("Consultando todas las pruebas en el sistema")
        val pruebas = examenVphRepository.findAll().map { it.toResponse() }
        log.info("Pruebas consultadas correctamente - Total: ${pruebas.size} registros")
        return pruebas
    }
}
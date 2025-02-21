package com.crisordonez.registro.service

import com.crisordonez.registro.model.mapper.ArchivoMapper.toEntity
import com.crisordonez.registro.model.mapper.EvolucionMapper.toEntity
import com.crisordonez.registro.model.mapper.PruebaMapper.toResponse
import com.crisordonez.registro.model.mapper.PruebaMapper.toUpdateResultado
import com.crisordonez.registro.model.requests.PruebaResultadoRequest
import com.crisordonez.registro.model.responses.PruebaResponse
import com.crisordonez.registro.repository.ArchivoRepository
import com.crisordonez.registro.repository.EvolucionRepository
import com.crisordonez.registro.repository.PruebaRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PruebaService(
    @Autowired private val pruebaRepository: PruebaRepository,
    @Autowired private val archivoRepository: ArchivoRepository,
    @Autowired private val evolucionRepository: EvolucionRepository
): PruebaServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun establecerResultadoPrueba(publicId: UUID, pruebaRequest: PruebaResultadoRequest) {
        try {
            log.info("Estableciendo resultado - Prueba: $publicId")
            val prueba = pruebaRepository.findByDispositivo(publicId).orElseThrow {
                throw Exception("No existe un dispositivo relacionado")
            }

            val archivo = if (pruebaRequest.archivo != null) {
                archivoRepository.save(pruebaRequest.archivo.toEntity(prueba))
            } else { null }

            val evolucion = if (pruebaRequest.evolucion != null) {
                evolucionRepository.save(pruebaRequest.evolucion.toEntity(prueba))
            } else { null }

            pruebaRepository.save(prueba.toUpdateResultado(pruebaRequest, archivo, evolucion))
            log.info("Resultado establecido correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getPrueba(publicId: UUID): PruebaResponse {
        try {
            log.info("Consultando prueba por dispositivo relacionado - Dispositivo: $publicId")
            val prueba = pruebaRepository.findByDispositivo(publicId).orElseThrow {
                throw Exception("No existe prueba relacionada con el dispositivo")
            }
            log.info("Prueba consultada correctamente")
            return prueba.toResponse()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getTodasPruebas(): List<PruebaResponse> {
        try {
            log.info("Consultando todas las pruebas en el sistema")
            val pruebas = pruebaRepository.findAll().map { it.toResponse() }
            log.info("Pruebas consultadas correctamente - Total: ${pruebas.size} registros")
            return pruebas
        } catch (e: Exception) {
            throw e
        }
    }
}
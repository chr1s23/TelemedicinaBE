package com.crisordonez.registro.service

import com.crisordonez.registro.model.mapper.EvolucionMapper.toEntity
import com.crisordonez.registro.model.mapper.EvolucionMapper.toResponse
import com.crisordonez.registro.model.requests.EvolucionRequest
import com.crisordonez.registro.model.responses.EvolucionResponse
import com.crisordonez.registro.repository.EvolucionRepository
import com.crisordonez.registro.repository.PruebaRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class EvolucionService(
    @Autowired private val evolucionRepository: EvolucionRepository,
    @Autowired private val pruebaRepository: PruebaRepository
) : EvolucionServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun crearEvolucion(publicId: UUID, evolucion: EvolucionRequest) {
        try {
            log.info("Creando evolucion")
            val prueba = pruebaRepository.findByDispositivo(publicId).orElseThrow {
                throw Exception("No existe una prueba relacionada al dispositivo referencia")
            }
            val evolucionEntity = evolucionRepository.save(evolucion.toEntity(prueba))
            prueba.evolucion.add(evolucionEntity)
            pruebaRepository.save(prueba)
            log.info("Evolucion creada correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getEvolucion(publicId: UUID): EvolucionResponse {
        try {
            log.info("Consultando evolucion - PublicId: $publicId")
            val evolucion = evolucionRepository.findByPublicId(publicId).orElseThrow {
                throw Exception("No existe el registro solicitado")
            }
            log.info("Evolucion consultada correctamente")
            return evolucion.toResponse()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getTodasEvoluciones(): List<EvolucionResponse> {
        try {
            log.info("Consultando todos los registros de evolucion")
            val registros = evolucionRepository.findAll().map { it.toResponse() }
            log.info("Registros consultados - Total: ${registros.size}")
            return registros
        } catch (e: Exception) {
            throw e
        }
    }

    override fun eliminarEvolucion(publicId: UUID) {
        try {
            log.info("Eliminando evolucion - PublicId: $publicId")
            val evolucion = evolucionRepository.findByPublicId(publicId).orElseThrow {
                throw Exception("No existe el registro solicitado")
            }
            evolucionRepository.delete(evolucion)
        } catch (e: Exception) {
            throw e
        }
    }

}
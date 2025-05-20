package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.ExamenVphEntity
import com.crisordonez.registro.model.enums.TipoArchivoEnum
import com.crisordonez.registro.model.mapper.EvolucionMapper.toEntity
import com.crisordonez.registro.model.mapper.ExamenVphMapper.toResponse
import com.crisordonez.registro.model.mapper.ExamenVphMapper.toUpdateResultado
import com.crisordonez.registro.model.requests.ExamenResultadoRequest
import com.crisordonez.registro.model.responses.ExamenVphResponse
import com.crisordonez.registro.repository.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*

@Service
class ExamenVphService(
    @Autowired private val examenVphRepository: ExamenVphRepository,
    @Autowired private val evolucionRepository: EvolucionRepository,
    @Autowired private val saludSexualRepository: SaludSexualRepository,
    @Autowired private val sesionChatRepository: SesionChatRepository,
    @Autowired private val dispositivoRegistradoRepository: DispositivoRegistradoRepository
) : ExamenVphServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun establecerResultadoPrueba(publicId: String, pruebaRequest: ExamenResultadoRequest) {
        try {
            log.info("Estableciendo resultado - Prueba: $publicId")
            val prueba = examenVphRepository.findByDispositivo(publicId).orElseThrow {
                throw Exception("No existe un dispositivo relacionado")
            }

            val evolucion = if (pruebaRequest.evolucion != null) {
                evolucionRepository.save(pruebaRequest.evolucion.toEntity(prueba))
            } else { null }

            examenVphRepository.save(prueba.toUpdateResultado(pruebaRequest, evolucion))
            log.info("Resultado establecido correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getPrueba(publicId: String): ExamenVphResponse {
        try {
            log.info("Consultando prueba por dispositivo relacionado - Dispositivo: $publicId")
            val prueba = examenVphRepository.findByDispositivo(publicId).orElseThrow {
                throw Exception("No existe prueba relacionada con el dispositivo")
            }
            log.info("Prueba consultada correctamente")
            return prueba.toResponse()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getTodasPruebas(): List<ExamenVphResponse> {
        try {
            log.info("Consultando todas las pruebas en el sistema")
            val pruebas = examenVphRepository.findAll().map { it.toResponse() }
            log.info("Pruebas consultadas correctamente - Total: ${pruebas.size} registros")
            return pruebas
        } catch (e: Exception) {
            throw e
        }
    }

    override fun subirResultadoPdf(
        pacienteId: Long,
        archivo: MultipartFile,
        nombre: String,
        dispositivo: String,
        diagnostico: String,
        genotiposStr: String?
    ) {
        try {
            log.info("Subiendo resultado PDF para paciente ID $pacienteId")

            val dispositivo = dispositivoRegistradoRepository.findByDispositivo(dispositivo)
                .orElseThrow { Exception("No se encontró el dispositivo con código $dispositivo") }

            val paciente = dispositivo.paciente
                ?: throw Exception("El dispositivo no tiene paciente asociado")

            val saludSexual = paciente.saludSexual
                ?: throw Exception("El paciente no tiene información de salud sexual")

            val sesionChat = paciente.sesionesChat.firstOrNull()
                ?: throw Exception("El paciente no tiene sesiones de chat registradas")

            val genotipos: List<String> = genotiposStr
                ?.takeIf { it.isNotBlank() }
                ?.let { jacksonObjectMapper().readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) }
                ?: emptyList()


            val nuevoExamen = ExamenVphEntity(
                fechaExamen = Date(),
                dispositivo = dispositivo.dispositivo,
                saludSexual = saludSexual,
                sesionChat = sesionChat,
                contenido = archivo.bytes,
                nombre = nombre,
                diagnostico = diagnostico,
                tamano = archivo.size,
                tipo = TipoArchivoEnum.PDF,
                genotipos = genotipos
            ).apply {
                creadoEn = LocalDateTime.now()
                actualizadoEn = LocalDateTime.now()
            }

            examenVphRepository.save(nuevoExamen)
            log.info("Resultado subido correctamente")
        } catch (e: Exception) {
            log.error("Error al subir resultado: ${e.message}")
            throw e
        }
    }
}

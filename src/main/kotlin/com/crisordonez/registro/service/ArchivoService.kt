package com.crisordonez.registro.service

import com.crisordonez.registro.model.mapper.ArchivoMapper.toEntity
import com.crisordonez.registro.model.mapper.ArchivoMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.ArchivoMapper.toResponse
import com.crisordonez.registro.model.requests.ArchivoRequest
import com.crisordonez.registro.model.responses.ArchivoResponse
import com.crisordonez.registro.repository.ArchivoRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArchivoService(
    @Autowired private val archivoRepository: ArchivoRepository
) : ArchivoServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun crearArchivo(archivo: ArchivoRequest) {
        try {
            log.info("Creando archivo")
            archivoRepository.save(archivo.toEntity())
            log.info("Archivo creado correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getArchivo(publicId: UUID): ArchivoResponse {
        try {
            log.info("Consultando archivo - PublicId: $publicId")
            val archivo = archivoRepository.findByPublicId(publicId).orElseThrow {
                throw Exception("No existe el archivo solicitado")
            }
            log.info("Archivo consultado correctamente")
            return archivo.toResponse()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getTodosArchivos(): List<ArchivoResponse> {
        try {
            log.info("Consultando todos los archivos del sistema")
            val archivos = archivoRepository.findAll().map { it.toResponse() }
            log.info("Archivos consultado - Total: ${archivos.size}")
            return archivos
        } catch (e: Exception) {
            throw e
        }
    }

    override fun editarArchivo(publicId: UUID, archivo: ArchivoRequest) {
        try {
            log.info("Editando archivo - PublicId: $publicId")
            val archivoExistente = archivoRepository.findByPublicId(publicId).orElseThrow {
                throw Exception("El archivo no existe")
            }
            archivoRepository.save(archivo.toEntityUpdated(archivoExistente))
            log.info("Archivo editado correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun eliminarArchivo(publicId: UUID) {
        try {
            log.info("Eliminando archivo - PublicId: $publicId")
            val archivo = archivoRepository.findByPublicId(publicId).orElseThrow {
                throw Exception("No existe el archivo requerido")
            }
            archivoRepository.delete(archivo)
            log.info("Archivo eliminado correctamente")
        } catch (e: Exception) {
            throw e
        }
    }
}
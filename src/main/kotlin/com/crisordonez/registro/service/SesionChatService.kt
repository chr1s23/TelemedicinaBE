package com.crisordonez.registro.service

import com.crisordonez.registro.model.mapper.PruebaMapper.toEntity
import com.crisordonez.registro.model.mapper.SaludSexualMapper.toEntity
import com.crisordonez.registro.model.mapper.SesionChatMapper.toEntity
import com.crisordonez.registro.model.mapper.SesionChatMapper.toResponse
import com.crisordonez.registro.model.requests.SesionChatRequest
import com.crisordonez.registro.model.responses.SesionChatResponse
import com.crisordonez.registro.repository.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SesionChatService(
    @Autowired private val sesionChatRepository: SesionChatRepository,
    @Autowired private val saludSexualRepository: SaludSexualRepository,
    @Autowired private val pruebaRepository: PruebaRepository,
    @Autowired private val pacienteRepository: PacienteRepository
): SesionChatServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun crearSesionChat(sesion: SesionChatRequest) {
        try {
            log.info("Creando sesion de chat - Usuario: ${sesion.cuentaPublicId}")
            val paciente = pacienteRepository.findByCuentaPublicId(sesion.cuentaPublicId).orElseThrow{
                throw Exception("La cuenta de usuario solicitada no existe")
            }
            val saludSexual = saludSexualRepository.save(sesion.saludSexual.toEntity())
            val sesionChat = sesionChatRepository.save(sesion.toEntity(paciente, saludSexual))

            if (sesion.prueba != null) {
                val prueba = pruebaRepository.save(sesion.prueba.toEntity(sesionChat))
                sesionChat.prueba = prueba
                sesionChatRepository.save(sesionChat)
            }

            paciente.sesionChat.add(sesionChat)
            pacienteRepository.save(paciente)
            log.info("Sesion de chat creada correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getSesionChat(publicId: UUID): List<SesionChatResponse> {
        try {
            log.info("Consultando sesiones de chat por usuario - PublicId: $publicId")
            val sesion = sesionChatRepository.findByPacienteCuentaPublicId(publicId)
            log.info("Sesiones de chat consultada correctamene - Total: ${sesion.size}")
            return sesion.map { it.toResponse() }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getTodosSesionChat(): List<SesionChatResponse> {
        try {
            log.info("Consultando todas las sesiones de chat")
            val sesiones = sesionChatRepository.findAll().map { it.toResponse() }
            log.info("Sesiones de chat consultadas - Total: ${sesiones.size}")
            return sesiones
        } catch (e: Exception) {
            throw e
        }
    }

    override fun deleteSesionChat(publicId: UUID) {
        try {
            log.info("Eliminando sesion de chat - PublicId: $publicId")
            val sesion = sesionChatRepository.findByPublicId(publicId).orElseThrow {
                throw Exception("La sesion de chat no existe")
            }
            sesionChatRepository.delete(sesion)
            log.info("Sesion de chat eliminada correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

}
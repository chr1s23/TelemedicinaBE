package com.crisordonez.registro.service

import com.crisordonez.registro.model.errors.BadRequestException
import com.crisordonez.registro.model.errors.NotFoundException
import com.crisordonez.registro.model.mapper.ExamenVphMapper.toEntity
import com.crisordonez.registro.model.mapper.ExamenVphMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.SaludSexualMapper.toEntity
import com.crisordonez.registro.model.mapper.SaludSexualMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.SesionChatMapper.toEntity
import com.crisordonez.registro.model.mapper.SesionChatMapper.toEntityUpdated
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
    @Autowired private val examenVphRepository: ExamenVphRepository,
    @Autowired private val pacienteRepository: PacienteRepository
): SesionChatServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun crearSesionChat(sesion: SesionChatRequest) {
        log.info("Creando sesion de chat - Usuario: ${sesion.cuentaPublicId}")
        val paciente = pacienteRepository.findByCuentaPublicId(sesion.cuentaPublicId).orElseThrow{
            throw NotFoundException("La cuenta de usuario solicitada no existe")
        }
        if (sesion.examenVph != null) {
            var sesionChat = sesionChatRepository.findByExamenVphDispositivo(sesion.examenVph.dispositivo)

            if (sesionChat == null) {
                sesionChat = sesionChatRepository.save(sesion.toEntity(paciente))
            }

            if (sesionChat.examenVph != null) {
                saludSexualRepository.save(sesion.examenVph.saludSexual.toEntityUpdated(sesionChat.examenVph!!.saludSexual))
                examenVphRepository.save(sesion.examenVph.toEntityUpdated(sesionChat.examenVph!!))
                sesionChatRepository.save(sesion.toEntityUpdated(sesionChat))
            } else {
                val saludSexual = saludSexualRepository.save(sesion.examenVph.saludSexual.toEntity())
                val prueba = examenVphRepository.save(sesion.examenVph.toEntity(sesionChat, saludSexual))
                saludSexual.examenVph = prueba
                sesionChat.examenVph = prueba
                sesionChatRepository.save(sesionChat)
                saludSexualRepository.save(saludSexual)
                paciente.sesionChat.add(sesionChat)
                pacienteRepository.save(paciente)
            }
        } else {
            throw BadRequestException("La informacion de la prueba es requerida")
        }
        log.info("Sesion de chat creada correctamente")
    }

    override fun getSesionChat(publicId: UUID): List<SesionChatResponse> {
        log.info("Consultando sesiones de chat por usuario - PublicId: $publicId")
        val sesion = sesionChatRepository.findByPacienteCuentaPublicId(publicId)
        log.info("Sesiones de chat consultada correctamene - Total: ${sesion.size}")
        return sesion.map { it.toResponse() }
    }

    override fun getTodosSesionChat(): List<SesionChatResponse> {
        log.info("Consultando todas las sesiones de chat")
        val sesiones = sesionChatRepository.findAll().map { it.toResponse() }
        log.info("Sesiones de chat consultadas - Total: ${sesiones.size}")
        return sesiones
    }

    override fun deleteSesionChat(publicId: UUID) {
        log.info("Eliminando sesion de chat - PublicId: $publicId")
        val sesion = sesionChatRepository.findByPublicId(publicId).orElseThrow {
            throw NotFoundException("La sesion de chat no existe")
        }
        sesionChatRepository.delete(sesion)
        log.info("Sesion de chat eliminada correctamente")
    }

}
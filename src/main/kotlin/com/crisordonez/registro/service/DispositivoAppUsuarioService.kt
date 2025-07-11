package com.crisordonez.registro.service

import com.crisordonez.registro.model.errors.NotFoundException
import com.crisordonez.registro.model.mapper.DispositivoAppUsuarioMapper.toEntity
import com.crisordonez.registro.model.mapper.DispositivoAppUsuarioMapper.toResponse
import com.crisordonez.registro.model.requests.DispositivoAppUsuarioRequest
import com.crisordonez.registro.model.responses.DispositivoAppUsuarioResponse
import com.crisordonez.registro.repository.DispositivoAppUsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DispositivoAppUsuarioService(
    @Autowired private val dispositivoAppRepository: DispositivoAppUsuarioRepository
) : DispositivoAppUsuarioServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun registrarDispositivo(request: DispositivoAppUsuarioRequest) {
        log.info("Registrando dispositivo para usuario ${request.usuarioPublicId}")
        dispositivoAppRepository.save(request.toEntity())
        log.info("Dispositivo registrado exitosamente.")
    }

    override fun listarDispositivosPorUsuario(usuarioPublicId: UUID): List<DispositivoAppUsuarioResponse> {
        log.info("Listando dispositivos del usuario $usuarioPublicId")
        return dispositivoAppRepository
            .findAllByUsuarioPublicId(usuarioPublicId)
            .map { it.toResponse() }
    }
}
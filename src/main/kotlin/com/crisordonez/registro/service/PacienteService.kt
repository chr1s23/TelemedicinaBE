// src/main/kotlin/com/crisordonez/registro/service/PacienteService.kt
package com.crisordonez.registro.service


import com.crisordonez.registro.model.entities.PacienteEntity
import com.crisordonez.registro.model.mapper.PacienteMapper.toEntity
import com.crisordonez.registro.model.errors.NotFoundException
import com.crisordonez.registro.model.mapper.DispositivoRegistradoMapper.toEntity
import com.crisordonez.registro.model.mapper.InformacionSocioeconomicaMapper.toEntity
import com.crisordonez.registro.model.mapper.InformacionSocioeconomicaMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.PacienteMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.PacienteMapper.toResponse
import com.crisordonez.registro.model.requests.DispositivoRegistradoRequest
import com.crisordonez.registro.model.requests.PacienteRequest
import com.crisordonez.registro.model.responses.PacienteResponse
import com.crisordonez.registro.repository.DispositivoRegistradoRepository
import com.crisordonez.registro.repository.InformacionSocioeconomicaRepository
import com.crisordonez.registro.repository.PacienteRepository
import com.crisordonez.registro.model.entities.DispositivoRegistradoEntity
import com.crisordonez.registro.model.enums.TipoAccionNotificacionEnum
import com.crisordonez.registro.model.enums.TipoNotificacionEnum
import com.crisordonez.registro.model.requests.NotificacionProgramadaRequest
import com.crisordonez.registro.model.requests.NotificacionRequest
import com.crisordonez.registro.service.NotificacionServiceInterface
import com.crisordonez.registro.utils.MensajesNotificacion.NOT_ACCION_NO_EXAMEN
import com.crisordonez.registro.utils.MensajesNotificacion.NOT_MENSAJE_NO_EXAMEN
import com.crisordonez.registro.utils.MensajesNotificacion.NOT_TIPO_ACCION_NO_EXAMEN
import com.crisordonez.registro.utils.MensajesNotificacion.NOT_TITULO_NO_EXAMEN


import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID


@Service
class PacienteService(
    @Autowired private val pacienteRepository: PacienteRepository,

    @Autowired private val dispositivoRegistradoRepository: DispositivoRegistradoRepository,

    @Autowired private val informacionSocioeconomicaRepository: InformacionSocioeconomicaRepository,
    private val notificacionService: NotificacionServiceInterface

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

    override fun registrarDispositivo(
        publicId: UUID,
        dispositivo: DispositivoRegistradoRequest
    ): String {
        val paciente = pacienteRepository.findByCuentaPublicId(publicId)
            .orElseThrow { NotFoundException("Paciente no encontrado") }
        val dispEnt: DispositivoRegistradoEntity =
            dispositivo.toEntity(paciente)
        val guardado: DispositivoRegistradoEntity =
            dispositivoRegistradoRepository.save(dispEnt)

        paciente.dispositivos.add(guardado)
        pacienteRepository.save(paciente)
        // Paso 1: Notificación puntual
        val notificacion = notificacionService.createNotification(
            NotificacionRequest(
                pacientePublicId = paciente.publicId,
                tipoNotificacion = TipoNotificacionEnum.RECORDATORIO_NO_EXAMEN,
                titulo = NOT_TITULO_NO_EXAMEN,
                mensaje = NOT_MENSAJE_NO_EXAMEN,
                tipoAccion = TipoAccionNotificacionEnum.valueOf(NOT_TIPO_ACCION_NO_EXAMEN),
                accion = NOT_ACCION_NO_EXAMEN
            )
        )

        // Paso 2: Notificación programada
        notificacionService.createScheduledNotification(
            requestNotificacion = NotificacionRequest(
                pacientePublicId = paciente.publicId,
                tipoNotificacion = TipoNotificacionEnum.RECORDATORIO_NO_EXAMEN,
                titulo = NOT_TITULO_NO_EXAMEN,
                mensaje = NOT_MENSAJE_NO_EXAMEN,
                tipoAccion = TipoAccionNotificacionEnum.valueOf(NOT_TIPO_ACCION_NO_EXAMEN),
                accion = NOT_ACCION_NO_EXAMEN
            ),
            requestProgramada = NotificacionProgramadaRequest(
                notificacionPublicId = notificacion.publicId,
                cronExpression = "0 */5 * * * *", // cada 5 minutos
                proxFecha = LocalDateTime.now().plusDays(3),
                limiteFecha = LocalDateTime.now().plusMonths(2)
            )
        )


        return guardado.dispositivo
    }

    override fun findByDispositivo(codigo: String): PacienteResponse? {
        log.info("Buscando paciente por dispositivo: $codigo")
        val disp = dispositivoRegistradoRepository.findByDispositivo(codigo)
            .orElseThrow { Exception("Dispositivo no registrado: $codigo") }
        val pacienteEnt = disp.paciente
        return pacienteEnt.toResponse()
    }
}
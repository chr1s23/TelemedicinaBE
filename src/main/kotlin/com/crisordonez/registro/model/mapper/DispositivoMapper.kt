package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.DispositivoEntity
import com.crisordonez.registro.model.entities.PacienteEntity
import com.crisordonez.registro.model.requests.DispositivoRequest
import com.crisordonez.registro.model.responses.DispositivoResponse
import java.text.SimpleDateFormat
import java.time.LocalDateTime

object DispositivoMapper {

    fun DispositivoRequest.toEntity(paciente: PacienteEntity): DispositivoEntity {
        return DispositivoEntity(
            dispositivo = this.dispositivo,
            fechaRegistro = LocalDateTime.now(),
            paciente = paciente
        )
    }

    fun DispositivoEntity.toResponse(): DispositivoResponse {
        return DispositivoResponse(
            publicId = this.publicId,
            dispositivo = this.dispositivo,
            fechaRegistro = this.fechaRegistro
        )
    }
}
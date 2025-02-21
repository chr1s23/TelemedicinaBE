package com.crisordonez.registro.service

import com.crisordonez.registro.model.requests.PacienteRequest
import com.crisordonez.registro.model.responses.PacienteResponse
import java.util.UUID

interface PacienteServiceInterface {

    fun editarPaciente(publicId: UUID, paciente: PacienteRequest)

    fun getPaciente(publicId: UUID): PacienteResponse

    fun getTodosPacientes(): List<PacienteResponse>

}
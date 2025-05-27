// src/main/kotlin/com/crisordonez/registro/service/DispositivoRegistradoService.kt
package com.crisordonez.registro.service

import com.crisordonez.registro.model.responses.DispositivoRegistradoResponse
import com.crisordonez.registro.repository.DispositivoRegistradoRepository
import com.crisordonez.registro.repository.PacienteRepository
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class DispositivoRegistradoService(
    private val dispRepo: DispositivoRegistradoRepository,
    private val pacienteRepo: PacienteRepository
) {
    /**
     * Busca un dispositivo por código,
     * lanza NoSuchElementException si no existe,
     * y trae el nombre del paciente asociado.
     */
    fun findByDispositivo(codigo: String): DispositivoRegistradoResponse {
        val disp = dispRepo.findByDispositivo(codigo)
            .orElseThrow { NoSuchElementException("Dispositivo '$codigo' no registrado") }

        val paciente = pacienteRepo.findById(disp.pacienteId)
            .orElseThrow { NoSuchElementException("Paciente id=${disp.pacienteId} no encontrado") }

        return DispositivoRegistradoResponse(
            dispositivo     = disp.dispositivo,
            pacienteId      = disp.pacienteId,
            pacienteNombre  = paciente.nombre
        )
    }
}

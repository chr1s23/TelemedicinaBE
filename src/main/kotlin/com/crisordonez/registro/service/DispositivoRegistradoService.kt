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
    //Busca un dispositivo por código lanza NoSuchElementException si no existe, y trae el nombre del paciente asociado.

    fun findByDispositivo(codigo: String): DispositivoRegistradoResponse {
        // 1) Busca el registro del dispositivo por su código
        val disp = dispRepo.findByDispositivo(codigo)
            .orElseThrow { NoSuchElementException("Dispositivo '$codigo' no registrado") }

        // 2) A partir del pacienteId del dispositivo, obtén el paciente
        val paciente = pacienteRepo.findById(disp.pacienteId)
            .orElseThrow { NoSuchElementException("Paciente id=${disp.pacienteId} no encontrado") }

        // 3) Mapea todo a tu DTO, incluyendo publicId y fechaRegistro
        return DispositivoRegistradoResponse(
            dispositivo     = disp.dispositivo,
            pacienteId      = disp.pacienteId,
            pacienteNombre  = paciente.nombre,
            publicId        = disp.publicId,
            fechaRegistro   = disp.fechaRegistro
        )
    }

}

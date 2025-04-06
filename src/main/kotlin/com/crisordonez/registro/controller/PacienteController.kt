package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.DispositivoRequest
import com.crisordonez.registro.model.requests.PacienteRequest
import com.crisordonez.registro.model.responses.PacienteResponse
import com.crisordonez.registro.service.PacienteServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/paciente")
class PacienteController {

    @Autowired
    lateinit var pacienteServiceInterface: PacienteServiceInterface

    @PutMapping("/editar/{publicId}")
    fun editarPaciente(@PathVariable publicId: UUID, @Valid @RequestBody paciente: PacienteRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(pacienteServiceInterface.editarPaciente(publicId, paciente))
    }

    @GetMapping("/usuario/{publicId}")
    fun getPaciente(@PathVariable publicId: UUID): ResponseEntity<PacienteResponse> {
        return ResponseEntity.ok(pacienteServiceInterface.getPaciente(publicId))
    }

    @GetMapping("/admin")
    fun getTodosPacientes(): ResponseEntity<List<PacienteResponse>> {
        return ResponseEntity.ok(pacienteServiceInterface.getTodosPacientes())
    }

    @PutMapping("/registrar-dispositivo/{publicId}")
    fun registrarDispositivo(@PathVariable publicId: UUID, @Valid @RequestBody dispositivo: DispositivoRequest): ResponseEntity<String> {
        return ResponseEntity.ok(pacienteServiceInterface.registrarDispositivo(publicId, dispositivo))
    }

}
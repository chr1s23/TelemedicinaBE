package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.MedicoRequest
import com.crisordonez.registro.model.responses.MedicoResponse
import com.crisordonez.registro.service.MedicoServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/medico")
class MedicoController {

    @Autowired
    lateinit var medicoServiceInterface: MedicoServiceInterface

    @PostMapping
    fun createMedico(@Valid @RequestBody medico: MedicoRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(medicoServiceInterface.createMedico(medico))
    }

    @GetMapping("/{publicId}")
    fun getMedico(@PathVariable publicId: UUID): ResponseEntity<MedicoResponse> {
        return ResponseEntity.ok(medicoServiceInterface.getMedico(publicId))
    }

    @GetMapping
    fun getAllMedicos(): ResponseEntity<List<MedicoResponse>> {
        return ResponseEntity.ok(medicoServiceInterface.getAllMedicos())
    }

    @PutMapping("/{publicId}")
    fun updateMedico(@PathVariable publicId: UUID, @Valid @RequestBody medico: MedicoRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(medicoServiceInterface.updateMedico(publicId, medico))
    }

    @DeleteMapping("/{publicId}")
    fun deleteMedico(@PathVariable publicId: UUID): ResponseEntity<Unit> {
        return ResponseEntity.ok(medicoServiceInterface.deleteMedico(publicId))
    }
}
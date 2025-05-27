package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.MedicoRequest
import com.crisordonez.registro.model.responses.MedicoResponse
import com.crisordonez.registro.service.MedicoServiceInterface
import com.crisordonez.registro.service.MedicoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/medicos")
class MedicoController(
    private val service: MedicoService
) {

    @GetMapping
    fun listAll(): List<MedicoResponse> =
        service.listarMedicos()

    @PostMapping
    fun create(@RequestBody req: MedicoRequest): ResponseEntity<MedicoResponse> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(service.crearMedico(req))

    // Cambiado a Long para buscar por id numérico
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): MedicoResponse =
        service.obtenerMedicoPorId(id)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,  // UUID para actualizar
        @RequestBody req: MedicoRequest
    ): MedicoResponse =
        service.actualizarMedico(id, req)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {  // UUID para eliminar
        service.eliminarMedico(id)
        return ResponseEntity.noContent().build()
    }
}

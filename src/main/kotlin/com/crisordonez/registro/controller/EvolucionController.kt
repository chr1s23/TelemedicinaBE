package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.EvolucionRequest
import com.crisordonez.registro.model.responses.EvolucionResponse
import com.crisordonez.registro.service.EvolucionServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/evolucion")
class EvolucionController {

    @Autowired
    lateinit var evolucionServiceInterface: EvolucionServiceInterface

    @PostMapping("/admin/{publicId}")
    fun crearEvolucion(@PathVariable publicId: String, @Valid @RequestBody evolucion: EvolucionRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(evolucionServiceInterface.crearEvolucion(publicId, evolucion))
    }

    @GetMapping("/admin/{publicId}")
    fun getEvolucion(@PathVariable publicId: UUID): ResponseEntity<EvolucionResponse> {
        return ResponseEntity.ok(evolucionServiceInterface.getEvolucion(publicId))
    }

    @GetMapping("/admin")
    fun getTodasEvoluciones(): ResponseEntity<List<EvolucionResponse>> {
        return ResponseEntity.ok(evolucionServiceInterface.getTodasEvoluciones())
    }

    @DeleteMapping("/admin/{publicId}")
    fun eliminarEvolucion(@PathVariable publicId: UUID): ResponseEntity<Unit> {
        return ResponseEntity.ok(evolucionServiceInterface.eliminarEvolucion(publicId))
    }

}
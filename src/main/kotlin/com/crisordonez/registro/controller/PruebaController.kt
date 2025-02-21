package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.PruebaResultadoRequest
import com.crisordonez.registro.model.responses.PruebaResponse
import com.crisordonez.registro.service.PruebaServiceInterface
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
@RequestMapping("/prueba")
class PruebaController {

    @Autowired
    lateinit var pruebaServiceInterface: PruebaServiceInterface

    @PutMapping("/admin/resultado/{publicId}")
    fun establecerResultado(@PathVariable publicId: UUID, @Valid @RequestBody prueba: PruebaResultadoRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(pruebaServiceInterface.establecerResultadoPrueba(publicId, prueba))
    }

    @GetMapping("/admin/{publicId}")
    fun getPrueba(@PathVariable publicId: UUID): ResponseEntity<PruebaResponse> {
        return ResponseEntity.ok(pruebaServiceInterface.getPrueba(publicId))
    }

    @GetMapping("/admin")
    fun getTodasPruebas(): ResponseEntity<List<PruebaResponse>> {
        return ResponseEntity.ok(pruebaServiceInterface.getTodasPruebas())
    }

}
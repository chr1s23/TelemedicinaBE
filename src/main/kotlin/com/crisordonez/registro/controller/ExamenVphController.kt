package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.ExamenResultadoRequest
import com.crisordonez.registro.model.responses.ExamenVphResponse
import com.crisordonez.registro.service.ExamenVphServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/prueba")
class ExamenVphController {

    @Autowired
    lateinit var examenVphServiceInterface: ExamenVphServiceInterface

    @PutMapping("/admin/resultado/{publicId}")
    fun establecerResultado(
        @PathVariable publicId: String,
        @Valid @RequestBody prueba: ExamenResultadoRequest,
        @RequestParam("archivo") archivo: MultipartFile
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(examenVphServiceInterface.establecerResultadoPrueba(publicId, prueba, archivo))
    }

    @GetMapping("/admin/{publicId}")
    fun getPrueba(@PathVariable publicId: String): ResponseEntity<ExamenVphResponse> {
        return ResponseEntity.ok(examenVphServiceInterface.getPrueba(publicId))
    }

    @GetMapping("/admin")
    fun getTodasPruebas(): ResponseEntity<List<ExamenVphResponse>> {
        return ResponseEntity.ok(examenVphServiceInterface.getTodasPruebas())
    }

}
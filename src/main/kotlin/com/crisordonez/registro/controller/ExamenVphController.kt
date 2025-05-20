package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.ExamenResultadoRequest
import com.crisordonez.registro.model.responses.ExamenVphResponse
import com.crisordonez.registro.service.ExamenVphServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/prueba")
class ExamenVphController {

    @Autowired
    lateinit var examenVphServiceInterface: ExamenVphServiceInterface

    @PutMapping("/admin/resultado/{publicId}")
    fun establecerResultado(
        @PathVariable publicId: String,
        @Valid @RequestBody prueba: ExamenResultadoRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(examenVphServiceInterface.establecerResultadoPrueba(publicId, prueba))
    }

    @GetMapping("/admin/{publicId}")
    fun getPrueba(@PathVariable publicId: String): ResponseEntity<ExamenVphResponse> {
        return ResponseEntity.ok(examenVphServiceInterface.getPrueba(publicId))
    }

    @GetMapping("/admin")
    fun getTodasPruebas(): ResponseEntity<List<ExamenVphResponse>> {
        return ResponseEntity.ok(examenVphServiceInterface.getTodasPruebas())
    }

    // NUEVO ENDPOINT PARA SUBIR PDF + DIAGNÓSTICO + RECOMENDACIÓN
    @PostMapping("/medico/subir/{pacienteId}")
    fun subirExamen(
        @PathVariable pacienteId: Long,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("nombre") nombre: String,
        @RequestParam("dispositivo") dispositivo: String,
        @RequestParam("diagnostico") diagnostico: String,
        @RequestParam("genotipos", required = false) genotiposStr: String?
    ): ResponseEntity<String> {
        examenVphServiceInterface.subirResultadoPdf(
            pacienteId = pacienteId,
            archivo = file,
            nombre = nombre,
            dispositivo = dispositivo,
            diagnostico = diagnostico,
            genotiposStr = genotiposStr

        )
        return ResponseEntity.ok("Examen subido correctamente")
    }

}

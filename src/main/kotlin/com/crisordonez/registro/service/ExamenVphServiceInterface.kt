package com.crisordonez.registro.service

import com.crisordonez.registro.model.requests.ExamenResultadoRequest
import com.crisordonez.registro.model.responses.ExamenVphResponse
import org.springframework.web.multipart.MultipartFile

interface ExamenVphServiceInterface {

    fun establecerResultadoPrueba(publicId: String, pruebaRequest: ExamenResultadoRequest)

    fun getPrueba(publicId: String): ExamenVphResponse

    fun getTodasPruebas(): List<ExamenVphResponse>

    fun subirResultadoPdf(
        pacienteId: Long,
        archivo: MultipartFile,
        nombre: String,
        dispositivo: String,
        diagnostico: String,
        genotiposStr: String?
    )
}

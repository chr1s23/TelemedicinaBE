package com.crisordonez.registro.service

import com.crisordonez.registro.model.requests.ExamenResultadoRequest
import com.crisordonez.registro.model.responses.ExamenVphResponse
import org.springframework.web.multipart.MultipartFile

interface ExamenVphServiceInterface {

    fun establecerResultadoPrueba(publicId: String, pruebaRequest: ExamenResultadoRequest, archivo: MultipartFile)

    fun getPrueba(publicId: String): ExamenVphResponse

    fun getTodasPruebas(): List<ExamenVphResponse>

}
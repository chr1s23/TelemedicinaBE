package com.crisordonez.registro.service

import com.crisordonez.registro.model.requests.ExamenResultadoRequest
import com.crisordonez.registro.model.responses.ExamenVphResponse
import java.util.UUID

interface ExamenVphServiceInterface {

    fun establecerResultadoPrueba(publicId: String, pruebaRequest: ExamenResultadoRequest)

    fun getPrueba(publicId: String): ExamenVphResponse

    fun getTodasPruebas(): List<ExamenVphResponse>

}
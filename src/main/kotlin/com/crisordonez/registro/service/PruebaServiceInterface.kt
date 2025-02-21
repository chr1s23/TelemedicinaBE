package com.crisordonez.registro.service

import com.crisordonez.registro.model.requests.PruebaResultadoRequest
import com.crisordonez.registro.model.responses.PruebaResponse
import java.util.UUID

interface PruebaServiceInterface {

    fun establecerResultadoPrueba(publicId: UUID, pruebaRequest: PruebaResultadoRequest)

    fun getPrueba(publicId: UUID): PruebaResponse

    fun getTodasPruebas(): List<PruebaResponse>

}
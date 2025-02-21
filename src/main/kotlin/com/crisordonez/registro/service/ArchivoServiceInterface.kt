package com.crisordonez.registro.service

import com.crisordonez.registro.model.requests.ArchivoRequest
import com.crisordonez.registro.model.responses.ArchivoResponse
import java.util.UUID

interface ArchivoServiceInterface {

    fun crearArchivo(publicId: UUID, archivo: ArchivoRequest)

    fun getArchivo(publicId: UUID): ArchivoResponse

    fun getTodosArchivos(): List<ArchivoResponse>

    fun editarArchivo(publicId: UUID, archivo: ArchivoRequest)

    fun eliminarArchivo(publicId: UUID)

}
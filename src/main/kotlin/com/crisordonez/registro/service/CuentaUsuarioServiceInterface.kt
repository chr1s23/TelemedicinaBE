package com.crisordonez.registro.service

import com.crisordonez.registro.model.requests.CuentaUsuarioRequest
import com.crisordonez.registro.model.responses.CuentaUsuarioResponse
import java.util.UUID

interface CuentaUsuarioServiceInterface {

    fun crearCuentaUsuario(cuentaUsuario: CuentaUsuarioRequest): String

    fun editarCuentaUsuario(publicId: UUID, cuentaUsuario: CuentaUsuarioRequest)

    fun getAllCuentas(): List<CuentaUsuarioResponse>

    fun getCuentaUsuario(publicId: UUID): CuentaUsuarioResponse

    fun autenticar(cuentaUsuario: CuentaUsuarioRequest): String

    fun eliminarCuentaUsuario(publicId: UUID)

}
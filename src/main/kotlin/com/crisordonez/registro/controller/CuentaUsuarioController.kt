package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.CuentaUsuarioRequest
import com.crisordonez.registro.model.responses.CuentaUsuarioResponse
import com.crisordonez.registro.service.CuentaUsuarioServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/usuarios")
class CuentaUsuarioController() {

    @Autowired
    lateinit var cuentaUsuarioServiceInterface: CuentaUsuarioServiceInterface

    @PostMapping("/registro")
    fun crearCuentaUsuario(@Valid @RequestBody cuenta: CuentaUsuarioRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.crearCuentaUsuario(cuenta))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody cuenta: CuentaUsuarioRequest): ResponseEntity<CuentaUsuarioResponse> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.login(cuenta))
    }

    @PostMapping("/autenticar")
    fun autenticar(@Valid @RequestBody usuario: CuentaUsuarioRequest): ResponseEntity<String> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.autenticar(usuario))
    }

}
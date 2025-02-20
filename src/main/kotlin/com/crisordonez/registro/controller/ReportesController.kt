package com.crisordonez.registro.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class ReportesController {

    @GetMapping("/home")
    fun getHome(): String {
        return "Welcome to home"
    }

    @GetMapping("/user/home")
    fun getUserHome(): String {
        return "Welcome to user home"
    }

    @GetMapping("/reportes")
    fun getReportes(): String {
        return "Reportes"
    }
}
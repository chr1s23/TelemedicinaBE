package com.crisordonez.registro.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DocsController {

    @GetMapping("/docs")
    fun redirectDocs(): String {
        return "redirect:/docs/index.html"
    }
}

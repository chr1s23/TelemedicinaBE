package com.crisordonez.registro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class RegistroApplication

fun main(args: Array<String>) {
	println("Iniciando servicio de registro...")
	runApplication<RegistroApplication>(*args)
	println("✅ Servicio de registro iniciado correctamente. ")
}

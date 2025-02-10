package com.crisordonez.registro

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.bind.annotation.CrossOrigin

@EnableJpaAuditing
@SpringBootApplication
@CrossOrigin
class RegistroApplication

private val log = LoggerFactory.getLogger(RegistroApplication::class.java)

fun main(args: Array<String>) {
	log.info("Iniciando servicio de registro...")
	runApplication<RegistroApplication>(*args)
	log.info("Servicio de registro iniciado correctamente.")
}

package com.crisordonez.registro.controller

import com.crisordonez.registro.model.mapper.mapPacienteToFhir
import com.crisordonez.registro.repository.PacienteRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID
import org.hl7.fhir.r4.model.Bundle
import ca.uhn.fhir.context.FhirContext

@RestController
@RequestMapping("/fhir/paciente")
class PacienteFhirController(
    private val pacienteRepository: PacienteRepository
) {



    @GetMapping("/{publicId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findByPublicId(@PathVariable publicId: UUID): ResponseEntity<String> {
        val paciente = pacienteRepository.findByPublicId(publicId)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado") }

        println("Paciente encontrado:")
        println("Nombre: ${paciente.nombre}, Fecha: ${paciente.fechaNacimiento}, Sexo: ${paciente.sexo}, ID: ${paciente.identificacion}")

        val fhirPatient = mapPacienteToFhir(paciente)

        val fhirContext = FhirContext.forR4()
        val json = fhirContext.newJsonParser().encodeResourceToString(fhirPatient)

        return ResponseEntity.ok(json)
    }

    // borrar paciente por publicId
    @DeleteMapping("/{publicId}")
    fun eliminarPacienteFhir(@PathVariable publicId: UUID): ResponseEntity<Unit> {
        val paciente = pacienteRepository.findByPublicId(publicId)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado") }

        pacienteRepository.delete(paciente)
        return ResponseEntity.noContent().build()
    }






    @GetMapping("/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllFhirPacientes(): ResponseEntity<String> {
        val pacientes = pacienteRepository.findAll()
        val pacientesFhir = pacientes.map { mapPacienteToFhir(it) }

        val fhirContext = FhirContext.forR4()
        val bundle = Bundle()
        bundle.type = Bundle.BundleType.COLLECTION

        pacientesFhir.forEach { patient ->
            bundle.addEntry().resource = patient
        }

        val json = fhirContext.newJsonParser().encodeResourceToString(bundle)
        return ResponseEntity.ok(json)
    }




}

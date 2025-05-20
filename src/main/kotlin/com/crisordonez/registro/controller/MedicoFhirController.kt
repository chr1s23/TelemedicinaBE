package com.crisordonez.registro.controller

import com.crisordonez.registro.model.entities.MedicoEntity
import com.crisordonez.registro.model.mapper.MedicoFhirMapper
import com.crisordonez.registro.model.mapper.MedicoMapper.toResponse
import com.crisordonez.registro.repository.MedicoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.hl7.fhir.r4.model.Bundle
import ca.uhn.fhir.context.FhirContext
import com.crisordonez.registro.model.mapper.MedicoFhirMapper.mapMedicoToFhir
import com.crisordonez.registro.model.mapper.MedicoMapper.toEntity
import java.util.UUID

@RestController
@RequestMapping("/fhir/medico")
class MedicoFhirController(
    private val medicoRepository: MedicoRepository
) {
/**
    // Obtener un médico por su publicId
    @GetMapping("/{publicId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findByPublicId(@PathVariable publicId: UUID): ResponseEntity<String> {
        val medico = medicoRepository.findByPublicId(publicId)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado") }

        println("Médico encontrado:")
        println("Nombre: ${medico.nombre}, Especialización: ${medico.especializacion}, Correo: ${medico.correo}")

        val fhirMedico = mapMedicoToFhir(medico)

        val fhirContext = FhirContext.forR4()
        val json = fhirContext.newJsonParser().encodeResourceToString(fhirMedico)

        return ResponseEntity.ok(json)
    }

    // Eliminar un médico por su publicId
    @DeleteMapping("/{publicId}")
    fun eliminarMedicoFhir(@PathVariable publicId: UUID): ResponseEntity<Unit> {
        val medico = medicoRepository.findByPublicId(publicId)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado") }

        medicoRepository.delete(medico)
        return ResponseEntity.noContent().build()
    }

    // Obtener todos los médicos en formato FHIR
    @GetMapping("/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllFhirMedicos(): ResponseEntity<String> {
        val medicos = medicoRepository.findAll()
        val medicosFhir = medicos.map { mapMedicoToFhir(it) }

        val fhirContext = FhirContext.forR4()
        val bundle = Bundle()
        bundle.type = Bundle.BundleType.COLLECTION

        medicosFhir.forEach { medico ->
            bundle.addEntry().resource = medico
        }

        val json = fhirContext.newJsonParser().encodeResourceToString(bundle)
        return ResponseEntity.ok(json)
    }

    // Crear un nuevo médico
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMedico(@RequestBody medico: MedicoEntity): ResponseEntity<String> {
        val medicoEntity = medicoRepository.save(medico)
        val fhirMedico = mapMedicoToFhir(medicoEntity)

        val fhirContext = FhirContext.forR4()
        val json = fhirContext.newJsonParser().encodeResourceToString(fhirMedico)

        return ResponseEntity.ok(json)
    }
 */
}

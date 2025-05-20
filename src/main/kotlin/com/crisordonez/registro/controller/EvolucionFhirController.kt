package com.crisordonez.registro.controller

import com.crisordonez.registro.model.mapper.EvolucionFhirMapper
import com.crisordonez.registro.repository.EvolucionRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import ca.uhn.fhir.context.FhirContext
import org.hl7.fhir.r4.model.Bundle
import java.util.*

@RestController
@RequestMapping("/fhir/evolucion")
class EvolucionFhirController(
    private val evolucionRepository: EvolucionRepository
) {

    @GetMapping("/{publicId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findByPublicId(@PathVariable publicId: UUID): ResponseEntity<String> {
        val evolucion = evolucionRepository.findByPublicId(publicId)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Evolución no encontrada") }

        val carePlan = EvolucionFhirMapper.mapEvolucionToFhir(evolucion)
        val fhirContext = FhirContext.forR4()
        val json = fhirContext.newJsonParser().encodeResourceToString(carePlan)

        return ResponseEntity.ok(json)
    }

    @GetMapping("/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllFhirEvoluciones(): ResponseEntity<String> {
        val evoluciones = evolucionRepository.findAll()
        val fhirContext = FhirContext.forR4()
        val bundle = Bundle().apply { type = Bundle.BundleType.COLLECTION }

        val resources = evoluciones.map { EvolucionFhirMapper.mapEvolucionToFhir(it) }
        resources.forEach { bundle.addEntry().resource = it }

        val json = fhirContext.newJsonParser().encodeResourceToString(bundle)
        return ResponseEntity.ok(json)
    }
}

package com.crisordonez.registro.model.mapper

import org.hl7.fhir.r4.model.*
import org.hl7.fhir.utilities.xhtml.XhtmlNode
import java.util.Date
import java.time.ZoneId
import java.util.UUID
import com.crisordonez.registro.model.entities.EvolucionEntity


object EvolucionFhirMapper {
    fun mapEvolucionToFhir(evolucion: EvolucionEntity): CarePlan {
        val carePlan = CarePlan()

        // ID único del recurso
        carePlan.id = evolucion.publicId.toString()

        // Texto narrativo para visualización
        carePlan.text = Narrative().apply {
            status = Narrative.NarrativeStatus.GENERATED
            divAsString = """<div xmlns="http://www.w3.org/1999/xhtml">
            Patient with temperature ${evolucion.temperatura}°C, pulse ${evolucion.pulso}, height ${evolucion.talla}m, weight ${evolucion.peso}kg.
        </div>""".trimIndent()
        }

        // Requeridos por FHIR
        carePlan.status = CarePlan.CarePlanStatus.ACTIVE
        carePlan.intent = CarePlan.CarePlanIntent.PLAN

        // Identificador (para trazabilidad externa)
        carePlan.addIdentifier(
            Identifier().apply {
                system = "http://example.com/evolucion/public-id"
                value = evolucion.publicId.toString()
            }
        )

        // Título y descripción médica (en inglés)
        carePlan.title = "Medical Evolution"
        carePlan.description = "Patient with temperature ${evolucion.temperatura}°C, pulse ${evolucion.pulso}, height ${evolucion.talla}m, weight ${evolucion.peso}kg."

        // Fecha de creación
        val creadoEn = evolucion.creadoEn
        carePlan.created = Date.from(creadoEn.atZone(ZoneId.systemDefault()).toInstant())

        // Referencia al paciente (extraído del examen VPH)
        val pacientePublicId = evolucion.examenVph.sesionChat.paciente.publicId
        carePlan.subject = Reference("Patient/$pacientePublicId")

        // Referencia al médico si existe
        evolucion.medico?.let { medico ->
            carePlan.addContributor(
                Reference("Practitioner/${medico.publicId}")
            )
        }

        return carePlan
    }

}

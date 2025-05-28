// src/main/kotlin/com/crisordonez/registro/model/mapper/ExamenVphFhirMapper.kt
package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.ExamenVphEntity
import org.hl7.fhir.r4.model.Attachment
import org.hl7.fhir.r4.model.CodeableConcept
import org.hl7.fhir.r4.model.Coding
import org.hl7.fhir.r4.model.DiagnosticReport
import org.hl7.fhir.r4.model.Reference
import java.time.ZoneId
import java.util.Date

object ExamenVphFhirMapper {

    /**
     * Mapea una entidad de examen VPH a un FHIR DiagnosticReport.

    fun mapToFhir(examen: ExamenVphEntity): DiagnosticReport {
        val dr = DiagnosticReport()

        // Asignar el ID FHIR a partir del publicId de nuestro examen
        dr.id = examen.publicId.toString()

        // Estado: PRELIMINARY si no hay fechaResultado, FINAL si ya fue generado
        dr.status = if (examen.fechaResultado != null) {
            DiagnosticReport.DiagnosticReportStatus.FINAL
        } else {
            DiagnosticReport.DiagnosticReportStatus.PRELIMINARY
        }

        // Código LOINC de HPV
        dr.code = CodeableConcept().apply {
            coding = listOf(
                Coding().apply {
                    system = "http://loinc.org"
                    code = "HPV"
                    display = "Human Papillomavirus DNA Test"
                }
            )
        }

        // Fecha de emisión (issued) convertida de LocalDateTime a java.util.Date
        examen.fechaResultado?.let { ldt ->
            val issuedDate: Date =
                Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant())
            dr.issued = issuedDate
        }

        // Categoría LAB
        dr.category = listOf(
            CodeableConcept().apply {
                coding = listOf(
                    Coding().apply {
                        system = "http://terminology.hl7.org/CodeSystem/v2-0074"
                        code = "LAB"
                        display = "Laboratory"
                    }
                )
            }
        )

        // Intérprete (última evolución registrada)
        examen.evolucion.maxByOrNull { it.creadoEn }
            ?.medico
            ?.let { medico ->
                dr.resultsInterpreter = listOf(
                    Reference("Practitioner/${medico.publicId}")
                )
            }

        // Adjuntar PDF si existe
        if (examen.contenido != null && examen.nombre != null) {
            dr.presentedForm = listOf(
                Attachment().apply {
                    contentType = when (examen.tipo) {
                        TipoArchivoEnum.PDF -> "application/pdf"
                        else                -> "application/octet-stream"
                    }
                    title = examen.nombre
                    data  = examen.contenido
                }
            )
        }

        return dr
    }
    */
}

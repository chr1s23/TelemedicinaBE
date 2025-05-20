package com.crisordonez.registro.model.entities

import org.hl7.fhir.r4.model.*
import java.util.*

object ExamenVphFhirMapper {

    fun mapToFhir(examen: ExamenVphEntity): DiagnosticReport {
        val diagnosticReport = DiagnosticReport()

        diagnosticReport.id = examen.publicId.toString()
        diagnosticReport.status = if (examen.fechaResultado != null) {
            DiagnosticReport.DiagnosticReportStatus.FINAL
        } else {
            DiagnosticReport.DiagnosticReportStatus.PRELIMINARY
        }

        diagnosticReport.code = CodeableConcept().apply {
            coding = listOf(Coding().apply {
                system = "http://loinc.org"
                code = "HPV"
                display = "Human Papillomavirus DNA Test"
            })
        }

       // diagnosticReport.effectiveDateTime = DateTimeType(examen.fechaExamen)

        examen.fechaResultado?.let {
            diagnosticReport.issued = it
        }

        diagnosticReport.category = listOf(CodeableConcept().apply {
            coding = listOf(Coding().apply {
                system = "http://terminology.hl7.org/CodeSystem/v2-0074"
                code = "LAB"
                display = "Laboratory"
            })
        })
      //  diagnosticReport.performer = listOf(Reference("Practitioner/${examen.sesionChat.medico?.publicId}"))


       // diagnosticReport.subject = Reference("Patient/${examen.saludSexual.paciente.publicId}")

        val ultimaEvolucion = examen.evolucion.maxByOrNull { it.creadoEn }
        ultimaEvolucion?.medico?.let {
            diagnosticReport.resultsInterpreter = listOf(Reference("Practitioner/${it.publicId}"))
        }

        if (examen.contenido != null && examen.nombre != null) {
            diagnosticReport.presentedForm = listOf(Attachment().apply {
                contentType = when (examen.tipo?.name?.lowercase()) {
                    "pdf" -> "application/pdf"
                    else -> "application/octet-stream"
                }
                title = examen.nombre
                data = examen.contenido
            })
        }

        return diagnosticReport
    }
}

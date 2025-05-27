package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.MedicoEntity
import org.hl7.fhir.r4.model.*

object MedicoFhirMapper {

    // Función para mapear MedicoEntity a un recurso Practitioner de FHIR
    fun mapMedicoToFhir(medico: MedicoEntity): Practitioner {
        val practitioner = Practitioner()
        // Agregar el publicId como identificador en FHIR
        val publicId = Identifier()
        publicId.system = "http://example.com/medico/public-id"  // El sistema que usarás para identificar este id
        publicId.value = medico.publicId.toString()  // Usamos el publicId del médico de la entidad
        practitioner.addIdentifier(publicId)  // Asignamos el identificador al Practitioner


        // Dividir nombre completo en nombre (given) y apellido (family)
        val nameParts = medico.nombre.split(" ")
        val name = HumanName()

        // Si tiene más de una palabra, usamos la última como apellido (family)
        if (nameParts.size > 1) {
            name.family = nameParts.last()  // Última palabra como apellido
            name.given = nameParts.dropLast(1).map { StringType(it) }  // El resto como nombre
        } else {
            name.given = listOf(StringType(medico.nombre))  // Si solo hay un nombre, se asigna todo a given
        }

        // Asignar el nombre completo al Practitioner
        practitioner.addName(name)

        // Correo electrónico del médico
        val telecom = ContactPoint()
        telecom.system = ContactPoint.ContactPointSystem.EMAIL
        telecom.value = medico.correo // Asigna el correo electrónico desde la entidad
        practitioner.addTelecom(telecom)

        // Especialización (agregarla como Qualification)
        val qualification = Practitioner.PractitionerQualificationComponent()
        val specialty = CodeableConcept()

        // Código SNOMED CT basado en la especialización
        specialty.addCoding(Coding()
            .setSystem("http://snomed.info/sct") // SNOMED CT system
            .setCode(when (medico.especializacion) {
                "Ginecología" -> "394586005"   // Gynecology
                "Cardiología" -> "394579002"   // Cardiology
                "Pediatría" -> "394537008"     // Pediatrics
                "Dermatología" -> "394582007"  // Dermatology
                "Neurología" -> "394591006"    // Neurology
                "Genetics service" -> "1304107001"
                else -> "999999999"            // Código por defecto si no se encuentra
            })
            .setDisplay(when (medico.especializacion) {
                "Ginecología" -> "Gynecology"
                "Cardiología" -> "Cardiology"
                "Pediatría" -> "Pediatrics"
                "Dermatología" -> "Dermatology"
                "Neurología" -> "Neurology"
                "Genética" -> "Genetics service"
                else -> medico.especializacion  // Display en español si no se encuentra el código
            })
        )

        qualification.code = specialty // Asigna el CodeableConcept a la cualificación
        practitioner.addQualification(qualification) // Añade la cualificación al practitioner

        return practitioner
    }
}

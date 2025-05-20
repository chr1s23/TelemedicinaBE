package com.crisordonez.registro.model.mapper

import org.hl7.fhir.r4.model.*
import com.crisordonez.registro.model.entities.PacienteEntity
import com.crisordonez.registro.model.enums.EstadoCivilEnum
import com.crisordonez.registro.model.enums.PaisEnum
import org.hl7.fhir.r4.model.StringType
import org.hl7.fhir.r4.model.Enumerations
import java.util.UUID
import org.hl7.fhir.r4.model.Narrative
import org.hl7.fhir.utilities.xhtml.XhtmlNode

// Función para traducir el sexo a inglés
fun traducirSexoEnIngles(sexo: String): String {
    return when (sexo) {
        "FEMENINO" -> "Female"
        "MASCULINO" -> "Male"
        else -> "Unknown"
    }
}

// Función para traducir el estado civil a inglés
fun traducirEstadoCivilEnIngles(estadoCivil: EstadoCivilEnum): String {
    return when (estadoCivil) {
        EstadoCivilEnum.SOLTERO -> "Never Married"
        EstadoCivilEnum.CASADO -> "Married"
        EstadoCivilEnum.VIUDO -> "Widowed"
        EstadoCivilEnum.DIVORCIADO -> "Divorced"
        EstadoCivilEnum.UNION_LIBRE -> "Common Law"
    }
}

fun mapPacienteToFhir(paciente: PacienteEntity): Bundle {
    // Crear el paciente
    val patient = Patient()

    // Nombre
    val nameParts = paciente.nombre.split(" ") // divide nombre completo
    val name = HumanName()

    if (nameParts.size > 1) {
        name.family = nameParts.last() // último como apellido
        name.given = nameParts.dropLast(1).map { StringType(it) } // resto como nombres
    } else {
        name.given = listOf(StringType(paciente.nombre)) // si solo hay un nombre
    }
    patient.addName(name)

    // Género
    patient.gender = when (paciente.sexo.name) {
        "FEMENINO" -> Enumerations.AdministrativeGender.FEMALE
        "MASCULINO" -> Enumerations.AdministrativeGender.MALE
        else -> Enumerations.AdministrativeGender.UNKNOWN
    }

    // Fecha de nacimiento
    patient.birthDate = paciente.fechaNacimiento

    // Identificación
    paciente.identificacion?.let {
        patient.addIdentifier(Identifier().setValue(it))
    }
    patient.addIdentifier(
        Identifier()
            .setSystem("https://example.com/paciente/public-id")
            .setValue(paciente.publicId.toString()) // El publicId es un UUID único
    )

    // Estado Civil
    val estadoCivilCode = when (paciente.estadoCivil) {
        EstadoCivilEnum.SOLTERO -> "S"   // Never Married
        EstadoCivilEnum.CASADO -> "M"    // Married
        EstadoCivilEnum.VIUDO -> "W"     // Widowed
        EstadoCivilEnum.DIVORCIADO -> "D"  // Divorced
        EstadoCivilEnum.UNION_LIBRE -> "C" // Common Law
    }

    val estadoCivilDisplay = traducirEstadoCivilEnIngles(paciente.estadoCivil)

    // Objeto Coding
    val maritalStatusCoding = Coding()
        .setSystem("http://terminology.hl7.org/CodeSystem/v3-MaritalStatus")
        .setCode(estadoCivilCode)
        .setDisplay(estadoCivilDisplay)

    // Crear un CodeableConcept que contenga el Coding
    val maritalStatus = CodeableConcept()
    maritalStatus.addCoding(maritalStatusCoding)

    // Crear un nuevo objeto Narrative
    val narrative = Narrative()
    narrative.status = Narrative.NarrativeStatus.GENERATED

    // Crear un XhtmlNode
    val xhtmlNode = XhtmlNode()
    val sexoEnIngles = traducirSexoEnIngles(paciente.sexo.name)

    // Determinar el pronombre adecuado (her/him) según el género
    val pronombre = if (paciente.sexo.name == "FEMENINO") "her" else "his"

    // Usar el pronombre adecuado en la narrativa
    xhtmlNode.setValue("<div>The patient is $sexoEnIngles, born on ${paciente.fechaNacimiento} and $pronombre marital status is $estadoCivilDisplay</div>")

    // Asignar el XhtmlNode a narrative.div
    narrative.div = xhtmlNode

    // Asignar el Narrative al paciente
    patient.text = narrative

    // Países según ISO 3166-1
    val paisNombre = mapOf(
        PaisEnum.ECUADOR to "EC",   // Ecuador - Código ISO 3166-1
        PaisEnum.COLOMBIA to "CO",  // Colombia - Código ISO 3166-1
        PaisEnum.VENEZUELA to "VE", // Venezuela - Código ISO 3166-1
        PaisEnum.PERU to "PE",      // Perú - Código ISO 3166-1
        PaisEnum.ARGENTINA to "AR", // Argentina - Código ISO 3166-1
        PaisEnum.BOLIVIA to "BO",   // Bolivia - Código ISO 3166-1
        PaisEnum.URUGUAY to "UY",   // Uruguay - Código ISO 3166-1
        PaisEnum.CHILE to "CL",     // Chile - Código ISO 3166-1
        PaisEnum.BRAZIL to "BR"     // Brasil - Código ISO 3166-1
    )

    // Se asigna el país al objeto Address
    val direccion = Address()
    direccion.country = paisNombre[paciente.pais]
    patient.address = listOf(direccion)

    // Crear el Bundle
    val bundle = Bundle()
    bundle.type = Bundle.BundleType.COLLECTION

    // Crear la entrada para el paciente en el Bundle
    val entry = Bundle.BundleEntryComponent()
    entry.fullUrl = "urn:uuid:${UUID.randomUUID()}"  // Asegúrate de que cada recurso tiene un fullUrl único
    entry.resource = patient

    // Añadir la entrada de Patient al Bundle
    bundle.addEntry(entry)

    return bundle
}

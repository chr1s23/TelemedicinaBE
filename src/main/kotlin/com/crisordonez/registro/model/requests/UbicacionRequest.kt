package com.crisordonez.registro.model.dto

import com.crisordonez.registro.model.entities.UbicacionEntity
import com.crisordonez.registro.model.enums.EstablecimientoEnum

data class UbicacionRequest(
    val nombre: String,
    val telefono: String,
    val direccion: String,
    val horario: String,
    val sitioWeb: String,
    val latitud: Double,
    val longitud: Double,
    val establecimiento: String // lo que llega en el JSON
) {
    fun toEntity(): UbicacionEntity {
        return UbicacionEntity(
            nombre = nombre,
            telefono = telefono,
            direccion = direccion,
            horario = horario,
            sitioWeb = sitioWeb,
            latitud = latitud,
            longitud = longitud,
            establecimiento = EstablecimientoEnum.valueOf(establecimiento)
        )
    }
}

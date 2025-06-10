package com.crisordonez.registro.model.responses

import com.crisordonez.registro.model.entities.UbicacionEntity
import java.util.UUID

data class UbicacionResponse(
    val publicId: UUID,
    val nombre: String,
    val telefono: String,
    val direccion: String,
    val horario: String,
    val sitioWeb: String,
    val latitud: Double,
    val longitud: Double,
    val establecimiento: String
) {
    companion object {
        fun fromEntity(entity: UbicacionEntity): UbicacionResponse {
            return UbicacionResponse(
                publicId = entity.publicId,
                nombre = entity.nombre,
                telefono = entity.telefono,
                direccion = entity.direccion,
                horario = entity.horario,
                sitioWeb = entity.sitioWeb,
                latitud = entity.latitud,
                longitud = entity.longitud,
                establecimiento = entity.establecimiento.toString()
            )
        }
    }
}
package com.crisordonez.registro.model.requests

data class UbicacionRequest(
    val nombre: String,
    val telefono: String,
    val direccion: String,
    val horario: String,
    val sitioWeb: String,
    val latitud: Double,
    val longitud: Double,
    val establecimiento: String // lo que llega en el JSON
)
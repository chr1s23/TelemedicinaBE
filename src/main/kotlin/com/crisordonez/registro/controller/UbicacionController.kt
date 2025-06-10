package com.crisordonez.registro.controller

import com.crisordonez.registro.model.dto.UbicacionRequest
import com.crisordonez.registro.model.enums.EstablecimientoEnum
import com.crisordonez.registro.model.responses.UbicacionResponse
import com.crisordonez.registro.service.UbicacionServiceInterface
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/ubicaciones")
class UbicacionController(
    private val ubicacionService: UbicacionServiceInterface
)
{
    @GetMapping
    fun obtenerUbicaciones(@RequestParam(required = false) establecimiento: EstablecimientoEnum?): List<UbicacionResponse> {
        val ubicaciones = if (establecimiento != null) {
            ubicacionService.listarPorEstablecimiento(establecimiento)
        } else {
            ubicacionService.obtenerTodas()
        }
        return ubicaciones?.map { UbicacionResponse.fromEntity(entity = it) } ?: emptyList()
    }


    @PostMapping
    fun crearUbicacion(@RequestBody ubicacionRequest: UbicacionRequest): UbicacionResponse {
        val entity = ubicacionService.crearUbicacion(ubicacionRequest.toEntity())
        return UbicacionResponse.fromEntity(entity)
    }

    @PostMapping("/lote")
    fun crearUbicaciones(@RequestBody ubicacionesRequest: List<UbicacionRequest>): List<UbicacionResponse> {
        val entidades = ubicacionService.crearUbicaciones(ubicacionesRequest.map { it.toEntity() })
        return entidades.map { UbicacionResponse.fromEntity(it) }
    }

    @PutMapping("/{publicId}")
    fun actualizarUbicacion(
        @PathVariable publicId: UUID,
        @RequestBody ubicacionRequest: UbicacionRequest
    ): UbicacionResponse? {
        val entity = ubicacionService.actualizarUbicacion(publicId, ubicacionRequest.toEntity())
        return entity?.let { UbicacionResponse.fromEntity(it) }
    }

    @DeleteMapping("/{publicId}")
    fun eliminarUbicacion(@PathVariable publicId: UUID) {
        ubicacionService.eliminarUbicacion(publicId)
    }
}

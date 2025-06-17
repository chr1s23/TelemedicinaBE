package com.crisordonez.registro.controller

import com.crisordonez.registro.model.dto.UbicacionRequest
import com.crisordonez.registro.model.enums.EstablecimientoEnum
import com.crisordonez.registro.model.responses.UbicacionResponse
import com.crisordonez.registro.service.UbicacionServiceInterface
import com.crisordonez.registro.model.mapper.UbicacionMapper
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/ubicaciones")
class UbicacionController(
    private val ubicacionService: UbicacionServiceInterface
) {

    @GetMapping
    fun obtenerUbicaciones(
        @RequestParam(required = false) establecimiento: EstablecimientoEnum?
    ): List<UbicacionResponse> {
        val ubicaciones = if (establecimiento != null) {
            ubicacionService.listarPorEstablecimiento(establecimiento)
        } else {
            ubicacionService.obtenerTodas()
        }
        return UbicacionMapper.toResponseList(ubicaciones ?: emptyList())
    }

    @PostMapping
    fun crearUbicacion(@RequestBody ubicacionRequest: UbicacionRequest): UbicacionResponse {
        val entity = ubicacionService.crearUbicacion(UbicacionMapper.toEntity(ubicacionRequest))
        return UbicacionMapper.toResponse(entity)
    }

    @PostMapping("/lote")
    fun crearUbicaciones(@RequestBody ubicacionesRequest: List<UbicacionRequest>): List<UbicacionResponse> {
        val entidades = ubicacionService.crearUbicaciones(
            ubicacionesRequest.map { UbicacionMapper.toEntity(it) }
        )
        return UbicacionMapper.toResponseList(entidades)
    }

    @PutMapping("/{publicId}")
    fun actualizarUbicacion(
        @PathVariable publicId: UUID,
        @RequestBody ubicacionRequest: UbicacionRequest
    ): UbicacionResponse? {
        val entity = ubicacionService.actualizarUbicacion(publicId, UbicacionMapper.toEntity(ubicacionRequest))
        return entity?.let { UbicacionMapper.toResponse(it) }
    }

    @DeleteMapping("/{publicId}")
    fun eliminarUbicacion(@PathVariable publicId: UUID) {
        ubicacionService.eliminarUbicacion(publicId)
    }
}

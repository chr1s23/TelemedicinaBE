package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.UbicacionEntity
import com.crisordonez.registro.model.enums.EstablecimientoEnum
import com.crisordonez.registro.repository.UbicacionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.UUID

@Service
class UbicacionService(
    private val ubicacionRepository: UbicacionRepository
) : UbicacionServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun obtenerTodas(): List<UbicacionEntity> {
        return ubicacionRepository.findAll()
    }

    override fun listarPorEstablecimiento(establecimiento: EstablecimientoEnum): List<UbicacionEntity>? {
        return ubicacionRepository.findByEstablecimiento(establecimiento)
    }

    override fun crearUbicacion(ubicacion: UbicacionEntity): UbicacionEntity {
        val existente = ubicacionRepository.findByNombreAndDireccionAndEstablecimiento(
            ubicacion.nombre,
            ubicacion.direccion,
            ubicacion.establecimiento
        )

        return if (existente == null) {
            ubicacionRepository.save(ubicacion)
        } else {
            // Si existe la misma combinación, actualiza campos básicos (sin cambiar establecimiento)
            existente.telefono = ubicacion.telefono
            existente.horario = ubicacion.horario
            existente.sitioWeb = ubicacion.sitioWeb
            existente.latitud = ubicacion.latitud
            existente.longitud = ubicacion.longitud
            existente.updatedAt = OffsetDateTime.now()

            ubicacionRepository.save(existente)
        }
    }

    override fun crearUbicaciones(ubicaciones: List<UbicacionEntity>): List<UbicacionEntity> {
        val resultado = mutableListOf<UbicacionEntity>()
        log.info("Creando ubicaciones en lote, total: ${ubicaciones.size}")
        for (ubicacion in ubicaciones) {
            val existente = ubicacionRepository.findByNombreAndDireccionAndEstablecimiento(
                ubicacion.nombre,
                ubicacion.direccion,
                ubicacion.establecimiento
            )
            val ubicacionFinal = if (existente == null) {
                ubicacionRepository.save(ubicacion)
            } else {
                existente.telefono = ubicacion.telefono
                existente.horario = ubicacion.horario
                existente.sitioWeb = ubicacion.sitioWeb
                existente.latitud = ubicacion.latitud
                existente.longitud = ubicacion.longitud
                existente.updatedAt = OffsetDateTime.now()

                ubicacionRepository.save(existente)
            }
            resultado.add(ubicacionFinal)
        }
        log.info("Ubicaciones creadas en lote correctamente, total: ${resultado.size}")
        return resultado
    }

    @Transactional
    override fun actualizarUbicacion(publicId: UUID, ubicacion: UbicacionEntity): UbicacionEntity? {
        log.info("Actualizando ubicación con publicId: $publicId")
        val existente = ubicacionRepository.findByPublicId(publicId) ?: return null

        existente.nombre = ubicacion.nombre
        existente.telefono = ubicacion.telefono
        existente.direccion = ubicacion.direccion
        existente.horario = ubicacion.horario
        existente.sitioWeb = ubicacion.sitioWeb
        existente.latitud = ubicacion.latitud
        existente.longitud = ubicacion.longitud
        existente.establecimiento = ubicacion.establecimiento
        existente.updatedAt = OffsetDateTime.now()

        log.info("Ubicación actualizada correctamente: $existente")

        return ubicacionRepository.save(existente)
    }

    @Transactional
    override fun eliminarUbicacion(publicId: UUID) {
        log.info("Eliminando ubicación con publicId: $publicId")
        ubicacionRepository.deleteByPublicId(publicId)
        log.info("Ubicación eliminada correctamente")
    }
}

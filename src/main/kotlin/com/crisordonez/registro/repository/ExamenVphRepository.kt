package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.ExamenVphEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional
import java.util.UUID
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional

interface ExamenVphRepository : CrudRepository<ExamenVphEntity, Long> {

    /**
     * Permite buscar un examen VPH a partir del código de dispositivo.
     */
    fun findByDispositivo(dispositivo: String): Optional<ExamenVphEntity>

    /**
     * Permite buscar un examen VPH a partir de su publicId.
     */
    fun findByPublicId(publicId: UUID): ExamenVphEntity?

    // Nuevo método para buscar examen por saludSexual.id
    @Query("SELECT e FROM ExamenVphEntity e WHERE e.saludSexual.id = :saludSexualId")
    fun findBySaludSexualId(@Param("saludSexualId") saludSexualId: Long): Optional<ExamenVphEntity>

    /**
     * Limpia solo los campos de contenido, fechaResultado, nombre, tamano, tipo y diagnostico
     * para el examen identificado por el código de dispositivo.
     */
    @Modifying
    @Transactional
    @Query(
        """
        UPDATE ExamenVphEntity e
        SET 
          e.contenido = NULL,
          e.fechaResultado = NULL,
          e.nombre = NULL,
          e.tamano = NULL,
          e.tipo = NULL,
          e.diagnostico = NULL
        WHERE e.dispositivo = :dispositivo
        """
    )
    fun clearFieldsByCodigo(@Param("dispositivo") dispositivo: String): Int
}

package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.NotificacionProgramadaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface NotificacionProgramadaRepository : JpaRepository<NotificacionProgramadaEntity, Long> {

    @Query("""
    SELECT np 
    FROM NotificacionProgramadaEntity np 
    WHERE np.proxFecha <= :now 
      AND np.programacionActiva = true
""")
    fun findAllByProxFechaBeforeAndProgramacionActivaIsTrue(
        @Param("now") now: LocalDateTime
    ): List<NotificacionProgramadaEntity>


    fun findByPublicId(publicId: UUID): Optional<NotificacionProgramadaEntity>


}

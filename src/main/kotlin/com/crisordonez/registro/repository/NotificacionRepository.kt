package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.NotificacionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

import java.util.*

interface NotificacionRepository : JpaRepository<NotificacionEntity, Long> {

    fun findByPublicId(publicId: UUID): Optional<NotificacionEntity>

    @Query("""
        SELECT n 
        FROM NotificacionEntity n 
        WHERE n.paciente.publicId = :pacientePublicId 
        ORDER BY n.fecha_creacion DESC
    """)
    fun findAllByPacientePublicIdOrderByFechaCreacionDesc(
        @Param("pacientePublicId") pacientePublicId: UUID
    ): List<NotificacionEntity>
    // Listar todas las notificaciones de un paciente no leídas
    @Query("""
        SELECT n 
        FROM NotificacionEntity n 
        WHERE n.paciente.publicId = :pacientePublicId 
        AND n.notificacion_leida = false 
        ORDER BY n.fecha_creacion DESC
    """)
    fun findAllByPacientePublicIdAndNotificacionNoLeidaOrderByFechaCreacionDesc(
        @Param("pacientePublicId") pacientePublicId: UUID): List<NotificacionEntity>
}

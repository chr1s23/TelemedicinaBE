package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.EncuestaSusEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EncuestaSusRepository : JpaRepository<EncuestaSusEntity, Long>

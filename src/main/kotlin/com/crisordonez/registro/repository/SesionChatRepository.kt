package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.SesionChatEntity
import org.springframework.data.repository.CrudRepository

interface SesionChatRepository: CrudRepository<SesionChatEntity, Long> {
}
package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.InformacionSocioeconomicaEntity
import org.springframework.data.repository.CrudRepository

interface InformacionSocioeconomicaRepository: CrudRepository<InformacionSocioeconomicaEntity, Long> {
}
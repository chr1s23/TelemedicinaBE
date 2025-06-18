package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.DispositivoAppUsuarioEntity
import com.crisordonez.registro.model.requests.DispositivoAppUsuarioRequest
import com.crisordonez.registro.model.responses.DispositivoAppUsuarioResponse

object DispositivoAppUsuarioMapper {

    fun DispositivoAppUsuarioRequest.toEntity(): DispositivoAppUsuarioEntity {
        return DispositivoAppUsuarioEntity(
            usuarioPublicId = this.usuarioPublicId,
            fcmToken = this.fcmToken,
            dispositivoId = this.dispositivoId
        )
    }

    fun DispositivoAppUsuarioEntity.toResponse(): DispositivoAppUsuarioResponse {
        return DispositivoAppUsuarioResponse(
            publicId = this.publicId,
            usuarioPublicId = this.usuarioPublicId,
            fcmToken = this.fcmToken,
            dispositivoId = this.dispositivoId,
            fechaRegistro = this.fechaRegistro
        )
    }
}

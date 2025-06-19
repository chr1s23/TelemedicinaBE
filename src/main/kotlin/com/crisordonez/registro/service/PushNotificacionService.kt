package com.crisordonez.registro.service


import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PushNotificacionService : PushNotificacionServiceInterface {

    private val logger = LoggerFactory.getLogger(PushNotificacionService::class.java)

    override fun enviarPush(titulo: String, mensaje: String, cuentaUsuario: CuentaUsuarioEntity) {
        logger.info("[Simulado] Enviando push a usuario ${cuentaUsuario.nombreUsuario}: [$titulo] $mensaje")
        // En integración real: usar cuentaUsuario.fcmToken para enviar push real
    }
}

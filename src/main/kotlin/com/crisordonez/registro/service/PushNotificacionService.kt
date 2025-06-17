package com.crisordonez.registro.service


import com.crisordonez.registro.model.entities.PacienteEntity
import com.crisordonez.registro.service.PushNotificacionServiceInterface
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PushNotificacionService : PushNotificacionServiceInterface {

    private val logger = LoggerFactory.getLogger(PushNotificacionService::class.java)

    override fun enviarPush(titulo: String, mensaje: String, paciente: PacienteEntity) {
        // Simulación del envío
        logger.info("[Simulado] Enviando push a paciente ${paciente.identificacion}: [$titulo] $mensaje")
        // En integración real: usar FCM y el token del paciente
    }
}

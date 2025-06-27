package com.crisordonez.registro.service

import com.crisordonez.registro.model.responses.NotificacionResponse
import org.slf4j.LoggerFactory
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service


@Service
open class PushNotificacionService(

) : PushNotificacionServiceInterface {

    private val logger = LoggerFactory.getLogger(PushNotificacionService::class.java)

    override fun enviarPushFCM(token: String?, notificacion: NotificacionResponse): String? {
        val notification = Notification.builder()
            .setTitle(notificacion.titulo)
            .setBody(notificacion.mensaje)
            .build()
        val message = Message.builder()
            .setToken(token)
            .setNotification(notification)
            .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
            .putData("tipoNotificacion", notificacion.tipoNotificacion.name)
            .putData("publicId", notificacion.publicId.toString())
            .putData("titulo", notificacion.titulo)
            .putData("mensaje", notificacion.mensaje)
            .putData("tipoAccion", notificacion.tipoAccion.name)
            .putData("accion", notificacion.accion ?: "")
            .putData("fechaEnvio", notificacion.fechaCreacion.toString())
            .putData("leido", notificacion.notificacionLeida.toString())
            .build()

        return try {
            val response = FirebaseMessaging.getInstance().send(message)
            logger.info("✅ Mensaje FCM enviado: $response")
            response
        } catch (e: Exception) {
            logger.info("❌ Error al enviar notificación: ${e.message}")
            null
        }
    }

}

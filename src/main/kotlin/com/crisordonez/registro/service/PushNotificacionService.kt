package com.crisordonez.registro.service

import org.slf4j.LoggerFactory
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service


@Service
open class PushNotificacionService(

) : PushNotificacionServiceInterface {

    private val logger = LoggerFactory.getLogger(PushNotificacionService::class.java)

    override fun enviarPushFCM(token: String?, titulo: String, mensaje: String): String? {
        val notification = Notification.builder()
            .setTitle(titulo)
            .setBody(mensaje)
            .build()

        val message = Message.builder()
            .setToken(token)
            .setNotification(notification)
            .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
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

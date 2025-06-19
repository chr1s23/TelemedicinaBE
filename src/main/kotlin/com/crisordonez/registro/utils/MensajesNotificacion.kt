package com.crisordonez.registro.utils

object MensajesNotificacion {

    // Para RECORDATORIO_NO_EXAMEN
    const val NOT_TITULO_NO_EXAMEN = "¡Recuerda realizarte el examen de automuestreo!"
    const val NOT_MENSAJE_NO_EXAMEN = "Haz clic aquí para ver el tutorial de cómo realizar el automuestreo."
    const val NOT_TIPO_ACCION_NO_EXAMEN = "AUTOMUESTREO"
    const val NOT_ACCION_NO_EXAMEN="https://miapp.com/video-tutorial"

    // Para RECORDATORIO_NO_ENTREGA_DISPOSITIVO
    const val NOT_TITULO_NO_ENTREGA = "¿Ya entregaste tu dispositivo?"
    const val NOT_MENSAJE_NO_ENTREGA = "Haz clic para confirmarlo y detener estos recordatorios."
    const val NOT_TIPO_ACCION_NO_ENTREGA = "VENTANA_EMERGENTE"
    const val NOT_ACCION_NO_ENTREGA = "https://miapp.com/confirmar-entrega"
    const val  CRON_EXPRESSION_NO_ENTREGA="0 0 10/3 * * *" // cada 3 días desde las 10am

    // Para BIENVENIDA
    const val NOT_TITULO_BIENVENIDA = "¡Bienvenida a CLIAS!"
    const val NOT_MENSAJE_BIENVENIDA = "Aquí puedes ver cómo funciona la app."
    const val NOT_TIPO_ACCION_BIENVENIDA = "VER_VIDEO"
    const val NOT_ACCION_BIENVENIDA = "https://miapp.com/bienvenida"
}
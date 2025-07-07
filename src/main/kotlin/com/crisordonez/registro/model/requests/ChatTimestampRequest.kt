package com.crisordonez.registro.model.requests

import java.time.LocalDateTime

data class ChatTimestampRequest (

    val initTimestamp: LocalDateTime,

    val endTimestamp: LocalDateTime
)
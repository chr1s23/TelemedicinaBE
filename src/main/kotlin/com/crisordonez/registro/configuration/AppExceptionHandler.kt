package com.crisordonez.registro.configuration

import com.crisordonez.registro.model.errors.AppErrorResponse
import com.crisordonez.registro.model.errors.AppException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AppExceptionHandler {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(AppException::class)
    fun handleAppException(ex: AppException): ResponseEntity<AppErrorResponse> {
        log.warn(ex.message)
        val response = AppErrorResponse(
            tipo = ex.httpStatus,
            mensaje = ex.message
        )
        return ResponseEntity(response, ex.httpStatus)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(ex: Exception): ResponseEntity<AppErrorResponse> {
        log.error(ex.message)
        val response = AppErrorResponse(
            tipo = HttpStatus.INTERNAL_SERVER_ERROR,
            mensaje = "Ha ocurrido un error inesperado"
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}
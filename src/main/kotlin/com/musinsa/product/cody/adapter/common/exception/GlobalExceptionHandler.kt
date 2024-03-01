package com.musinsa.product.cody.adapter.common.exception

import com.musinsa.product.cody.application.exception.ApplicationException
import com.musinsa.product.cody.application.exception.ErrorCode
import com.musinsa.product.cody.application.exception.NotFoundException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(NOT_FOUND)
            .body(e.errorCode.toResponse())
    }

    @ExceptionHandler(ApplicationException::class)
    fun handleApplicationException(e: ApplicationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(BAD_REQUEST)
            .body(e.errorCode.toResponse())
    }

    @ExceptionHandler(Exception::class)
    fun handleUncaughtException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    code = "INTERNAL_SERVER_ERROR",
                    message = "예상치 못한 오류가 발생했습니다."
                )
            )
    }

    private fun ErrorCode.toResponse(): ErrorResponse {
        return ErrorResponse(
            code = this.toString(),
            message = this.message
        )
    }

    data class ErrorResponse(
        val code: String,
        val message: String
    )
}
package com.musinsa.product.cody.application.exception

open class ApplicationException(
    val errorCode: ErrorCode,
    override val cause: Throwable? = null
) : RuntimeException(errorCode.message, cause)

open class NotFoundException(
    errorCode: ErrorCode,
    override val cause: Throwable? = null
) : ApplicationException(
    errorCode = errorCode,
    cause = cause
)
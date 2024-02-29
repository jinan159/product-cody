package com.musinsa.product.search.application.admin.port.`in`

import com.musinsa.product.search.application.exception.ApplicationException
import com.musinsa.product.search.application.exception.ErrorCode.PRODUCT_CREATE_FAILED
import java.math.BigDecimal

interface ProductCreateUseCase {
    fun create(request: CreateRequest): Response

    data class CreateRequest(
        val brandId: Long,
        val categoryId: Long,
        val name: String,
        val amount: BigDecimal
    )

    data class Response(
        val id: Long
    )

    class ProductCreateFailedException(
        override val cause: Throwable?
    ) : ApplicationException(
        errorCode = PRODUCT_CREATE_FAILED,
        cause = cause
    )
}
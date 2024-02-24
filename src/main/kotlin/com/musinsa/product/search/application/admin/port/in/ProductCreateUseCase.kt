package com.musinsa.product.search.application.admin.port.`in`

import java.math.BigDecimal
import java.util.Currency

interface ProductCreateUseCase {
    fun create(request: com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.CreateRequest): com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.Response

    data class CreateRequest(
        val brandId: Long,
        val categoryId: Long,
        val name: String,
        val currency: Currency,
        val amount: BigDecimal
    )

    data class Response(
        val id: Long
    )

    class ProductCreateFailedException(
        override val cause: Throwable?
    ) : RuntimeException(
        "상품 생성에 실패했습니다",
        cause
    )
}
package com.musinsa.product.search.application.admin.port.`in`

import java.math.BigDecimal

interface ProductUpdateUseCase {
    fun update(request: UpdateRequest)

    data class UpdateRequest(
        val id: Long,
        val brandId: Long?,
        val categoryId: Long?,
        val name: String?,
        val amount: BigDecimal?
    )

    class ProductUpdateFailedException(
        override val cause: Throwable?
    ) : RuntimeException(
        "상품 수정에 실패했습니다",
        cause
    )
}
package com.musinsa.product.cody.application.admin.port.`in`

import com.musinsa.product.cody.application.exception.ApplicationException
import com.musinsa.product.cody.application.exception.ErrorCode.PRODUCT_UPDATE_FAILED
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
    ) : ApplicationException(
        errorCode = PRODUCT_UPDATE_FAILED,
        cause = cause
    )
}
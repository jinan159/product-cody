package com.musinsa.recommand.application.admin.port.`in`

import java.math.BigDecimal
import java.util.Currency

interface ProductUpdateUseCase {
    fun update(request: UpdateRequest)

    data class UpdateRequest(
        val productId: Long,
        val brandId: Long?,
        val categoryId: Long?,
        val name: String?,
        val currency: Currency?,
        val amount: BigDecimal?
    )
}
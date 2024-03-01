package com.musinsa.product.cody.application.product.port.`in`

import com.musinsa.product.cody.application.exception.ErrorCode.LOWEST_PRICE_SINGLE_BRAND_NOT_FOUND
import com.musinsa.product.cody.application.exception.NotFoundException
import java.math.BigDecimal

interface LowestPriceSingleBrandCodySelectUseCase {
    fun select(): Response

    data class Response(
        val minPrice: MinPrice
    )

    data class MinPrice(
        val brandName: String,
        val categories: List<CategoryAndPrice>,
        val totalPrice: BigDecimal
    ) {
        data class CategoryAndPrice (
            val categoryName: String,
            val price: BigDecimal
        )
    }

    class LowestPriceSingleBrandCodyNotFoundException : NotFoundException(
        LOWEST_PRICE_SINGLE_BRAND_NOT_FOUND
    )
}
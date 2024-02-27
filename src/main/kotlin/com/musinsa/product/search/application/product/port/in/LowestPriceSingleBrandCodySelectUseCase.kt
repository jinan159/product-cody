package com.musinsa.product.search.application.product.port.`in`

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

    class LowestPriceSingleBrandCodyNotFoundException : RuntimeException(
        "최저 가격인 단일 브랜드 코디를 찾을 수 없습니다"
    )
}
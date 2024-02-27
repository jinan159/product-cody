package com.musinsa.product.search.application.product.port.`in`

import java.math.BigDecimal

interface ProductPriceExtremesSearchUseCase {
    fun search(categoryName: String): Response

    data class Response(
        val categoryName: String,
        val minPrices: List<BrandAndPrice>,
        val maxPrices: List<BrandAndPrice>
    )

    data class BrandAndPrice(
        val brandName: String,
        val price: BigDecimal
    )

    class ProductPriceExtremesNotFoundException : RuntimeException(
        "최저, 최고 가격 상품을 찾을 수 없습니다"
    )
}
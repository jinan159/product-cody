package com.musinsa.product.search.application.product.port.`in`

import com.musinsa.product.search.application.exception.ErrorCode
import com.musinsa.product.search.application.exception.NotFoundException
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

    class ProductPriceExtremesNotFoundException : NotFoundException(
        ErrorCode.PRODUCT_PRICE_EXTREMES_NOT_FOUND
    )
}
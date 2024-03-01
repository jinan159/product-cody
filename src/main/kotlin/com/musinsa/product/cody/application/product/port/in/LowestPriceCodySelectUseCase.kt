package com.musinsa.product.cody.application.product.port.`in`

import java.math.BigDecimal

interface LowestPriceCodySelectUseCase {
    fun select(): Response

    data class Response(
        val products: List<Product>,
        val totalPrice: BigDecimal
    ) {
        data class Product(
            val categoryName: String,
            val brandName: String,
            val price: BigDecimal
        )
    }
}
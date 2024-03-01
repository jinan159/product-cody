package com.musinsa.product.cody.application.product.port.out

import java.math.BigDecimal

interface LowestPriceSingleBrandCodySelectRepository {
    fun select(): SelectResult?

    data class SelectResult(
        val brandName: String,
        val categories: List<CategoryAndPrice>,
    )

    data class CategoryAndPrice(
        val categoryName: String,
        val price: BigDecimal
    )
}

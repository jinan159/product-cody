package com.musinsa.product.search.application.product.port.out

import java.math.BigDecimal

interface LowestPriceCodySelectRepository {
    fun select(): SelectResult

    data class SelectResult(
        val items: List<Item>
    )

    data class Item(
        val categoryName: String,
        val brandName: String,
        val amount: BigDecimal
    )
}
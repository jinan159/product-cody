package com.musinsa.product.search.application.product.port.out

import java.math.BigDecimal

interface ProductPriceExtremesSearchRepository {
    fun searchCategoryIdByCategoryName(categoryName: String): CategorySearchResult?
    fun search(categoryId: Long): SearchResult?

    data class CategorySearchResult(
        val id: Long,
        val name: String
    )

    data class SearchResult(
        val minPrice: Item,
        val maxPrice: Item
    )

    data class Item(
        val brandName: String,
        val price: BigDecimal
    )
}

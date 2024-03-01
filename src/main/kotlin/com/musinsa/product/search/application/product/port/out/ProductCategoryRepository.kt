package com.musinsa.product.search.application.product.port.out

interface ProductCategoryRepository {
    fun findById(id: Long): ProductCategory?

    data class ProductCategory(
        val productId: Long,
        val categoryName: String
    )
}
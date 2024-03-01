package com.musinsa.product.cody.application.product.port.`in`

interface ProductGetUseCase {
    fun get(id: Long): Response

    data class Response(
        val productId: Long,
        val categoryName: String
    )
}
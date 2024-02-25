package com.musinsa.product.search.application.admin.port.`in`

interface BrandCreateUseCase {
    fun create(request: CreateRequest): Response

    data class CreateRequest(
        val name: String
    )

    data class Response(
        val id: Long
    )
}
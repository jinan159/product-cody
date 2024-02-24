package com.musinsa.product.search.application.admin.port.`in`

interface BrandCreateUseCase {
    fun create(request: com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase.CreateRequest): com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase.Response

    data class CreateRequest(
        val name: String
    )

    data class Response(
        val id: Long
    )
}
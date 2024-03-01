package com.musinsa.product.cody.application.admin.port.`in`

interface BrandCreateUseCase {
    fun create(request: CreateRequest): Response

    data class CreateRequest(
        val name: String
    )

    data class Response(
        val id: Long
    )
}
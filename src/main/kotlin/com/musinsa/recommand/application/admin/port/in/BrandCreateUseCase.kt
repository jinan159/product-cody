package com.musinsa.recommand.application.admin.port.`in`

interface BrandCreateUseCase {
    fun create(request: CreateRequest)

    data class CreateRequest(
        val name: String
    )
}
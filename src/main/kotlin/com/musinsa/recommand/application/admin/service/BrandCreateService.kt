package com.musinsa.recommand.application.admin.service

import com.musinsa.recommand.application.admin.port.`in`.BrandCreateUseCase
import com.musinsa.recommand.application.admin.port.`in`.BrandCreateUseCase.CreateRequest
import com.musinsa.recommand.application.admin.port.out.BrandRepository
import com.musinsa.recommand.domain.Brand

class BrandCreateService(
    private val brandRepository: BrandRepository
) : BrandCreateUseCase {
    override fun create(request: CreateRequest) {
        val brand = Brand(name = request.name)

        brandRepository.save(brand)
    }
}
package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase
import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase.CreateRequest
import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase.Response
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.domain.Brand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BrandCreateService(
    private val brandRepository: BrandRepository
) : BrandCreateUseCase {
    override fun create(request: CreateRequest): Response {
        val savedBrand = brandRepository.save(
            Brand(name = request.name)
        )

        return Response(
            id = savedBrand.id!!
        )
    }
}
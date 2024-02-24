package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase
import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase.CreateRequest
import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase.Response
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.domain.Brand
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class BrandCreateService(
    private val brandRepository: com.musinsa.product.search.application.admin.port.out.BrandRepository
) : com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase {
    override fun create(request: CreateRequest): Response {
        val savedBrand = brandRepository.save(
            com.musinsa.product.search.domain.Brand(name = request.name)
        )

        return Response(
            id = savedBrand.id!!
        )
    }
}
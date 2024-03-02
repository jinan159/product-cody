package com.musinsa.product.cody.application.admin.service

import com.musinsa.product.cody.application.admin.port.`in`.CategoryCreateUseCase
import com.musinsa.product.cody.application.admin.port.`in`.CategoryCreateUseCase.CreateRequest
import com.musinsa.product.cody.application.admin.port.`in`.CategoryCreateUseCase.Response
import com.musinsa.product.cody.application.admin.port.out.CategoryRepository
import com.musinsa.product.cody.domain.Category
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryCreateService(
    private val categoryRepository: CategoryRepository
) : CategoryCreateUseCase {
    override fun create(request: CreateRequest): Response {
        val savedCategory = categoryRepository.save(
            Category(name = request.name)
        )

        return Response(
            id = savedCategory.id!!
        )
    }
}
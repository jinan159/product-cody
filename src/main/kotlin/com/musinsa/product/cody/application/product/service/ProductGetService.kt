package com.musinsa.product.cody.application.product.service

import com.musinsa.product.cody.application.exception.ProductNotFoundException
import com.musinsa.product.cody.application.product.port.`in`.ProductGetUseCase
import com.musinsa.product.cody.application.product.port.`in`.ProductGetUseCase.Response
import com.musinsa.product.cody.application.product.port.out.ProductCategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductGetService(
    private val repository: ProductCategoryRepository
) : ProductGetUseCase {
    override fun get(id: Long): Response {
        val productCategory = repository.findById(id)
            ?: throw ProductNotFoundException()

        return Response(
            productId = productCategory.productId,
            categoryName = productCategory.categoryName
        )
    }
}
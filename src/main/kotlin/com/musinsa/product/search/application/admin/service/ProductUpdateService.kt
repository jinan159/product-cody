package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.ProductUpdateUseCase
import com.musinsa.product.search.application.admin.port.`in`.ProductUpdateUseCase.ProductUpdateFailedException
import com.musinsa.product.search.application.admin.port.`in`.ProductUpdateUseCase.UpdateRequest
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.application.admin.port.out.CategoryRepository
import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.application.exception.BrandNotFoundException
import com.musinsa.product.search.application.exception.CategoryNotFoundException
import com.musinsa.product.search.application.exception.ProductNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ProductUpdateService(
    private val brandRepository: com.musinsa.product.search.application.admin.port.out.BrandRepository,
    private val categoryRepository: com.musinsa.product.search.application.admin.port.out.CategoryRepository,
    private val productRepository: com.musinsa.product.search.application.admin.port.out.ProductRepository
) : com.musinsa.product.search.application.admin.port.`in`.ProductUpdateUseCase {
    override fun update(request: UpdateRequest) {
        request.validate()

        val product = productRepository.findById(request.id)
            ?: throw com.musinsa.product.search.application.exception.ProductNotFoundException()

        try {
            with(request) {
                product.update(
                    brandId = brandId,
                    categoryId = categoryId,
                    name = name,
                    currency = currency,
                    amount = amount
                )
            }
        } catch (e: Exception) {
            throw ProductUpdateFailedException(e)
        }

        productRepository.save(product)
    }

    private fun UpdateRequest.validate() {
        if (brandId != null && !brandRepository.existsById(brandId)) throw com.musinsa.product.search.application.exception.BrandNotFoundException()
        if (categoryId != null && !categoryRepository.existsById(categoryId)) throw com.musinsa.product.search.application.exception.CategoryNotFoundException()
    }
}
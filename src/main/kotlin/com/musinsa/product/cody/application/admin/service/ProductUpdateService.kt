package com.musinsa.product.cody.application.admin.service

import com.musinsa.product.cody.application.admin.port.`in`.ProductUpdateUseCase
import com.musinsa.product.cody.application.admin.port.`in`.ProductUpdateUseCase.ProductUpdateFailedException
import com.musinsa.product.cody.application.admin.port.`in`.ProductUpdateUseCase.UpdateRequest
import com.musinsa.product.cody.application.admin.port.out.BrandRepository
import com.musinsa.product.cody.application.admin.port.out.CategoryRepository
import com.musinsa.product.cody.application.admin.port.out.ProductChangedEventPublisher
import com.musinsa.product.cody.application.admin.port.out.ProductRepository
import com.musinsa.product.cody.application.exception.BrandNotFoundException
import com.musinsa.product.cody.application.exception.CategoryNotFoundException
import com.musinsa.product.cody.application.exception.ProductNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductUpdateService(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val productChangedEventPublisher: ProductChangedEventPublisher
) : ProductUpdateUseCase {
    override fun update(request: UpdateRequest) {
        request.validate()

        val product = productRepository.findById(request.id)
            ?: throw ProductNotFoundException()

        try {
            with(request) {
                product.update(
                    brandId = brandId,
                    categoryId = categoryId,
                    name = name,
                    amount = amount
                )
            }
        } catch (e: Exception) {
            throw ProductUpdateFailedException(e)
        }

        productRepository.save(product)

        productChangedEventPublisher.publish(
            ProductChangedEventPublisher.EventPayload(product.id!!)
        )
    }

    private fun UpdateRequest.validate() {
        if (brandId != null && !brandRepository.existsById(brandId)) throw BrandNotFoundException()
        if (categoryId != null && !categoryRepository.existsById(categoryId)) throw CategoryNotFoundException()
    }
}
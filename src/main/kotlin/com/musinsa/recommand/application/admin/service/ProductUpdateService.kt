package com.musinsa.recommand.application.admin.service

import com.musinsa.recommand.application.admin.port.`in`.ProductUpdateUseCase
import com.musinsa.recommand.application.admin.port.`in`.ProductUpdateUseCase.UpdateRequest
import com.musinsa.recommand.application.admin.port.out.ProductRepository
import com.musinsa.recommand.application.exception.ProductNotFoundException

class ProductUpdateService(
    private val productValidator: ProductValidator,
    private val productRepository: ProductRepository
) : ProductUpdateUseCase {
    override fun update(request: UpdateRequest) {
        request.validate()
        val product = productRepository.findById(request.productId)
            ?: throw ProductNotFoundException()

        with(request) {
            product.update(
                brandId = brandId,
                categoryId = categoryId,
                name = name,
                currency = currency,
                amount = amount
            )
        }

        productRepository.save(product)
    }

    private fun UpdateRequest.validate() {
        with(this) {
            productValidator.validate(
                brandId = brandId,
                categoryId = categoryId
            )
        }
    }
}
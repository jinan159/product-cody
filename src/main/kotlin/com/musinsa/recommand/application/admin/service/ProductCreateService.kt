package com.musinsa.recommand.application.admin.service

import com.musinsa.recommand.application.admin.port.`in`.ProductCreateUseCase
import com.musinsa.recommand.application.admin.port.`in`.ProductCreateUseCase.CreateRequest
import com.musinsa.recommand.application.admin.port.out.ProductRepository
import com.musinsa.recommand.domain.Product
import com.musinsa.recommand.domain.ProductPrice

class ProductCreateService(
    private val productValidator: ProductValidator,
    private val productRepository: ProductRepository
) : ProductCreateUseCase {
    override fun create(request: CreateRequest) {
        request.validate()

        val product = with(request) {
            Product(
                brandId = brandId,
                categoryId = categoryId,
                name = name,
                price = ProductPrice(
                    currency = currency,
                    amount = amount
                )
            )
        }

        productRepository.save(product)
    }

    private fun CreateRequest.validate() {
        with(this) {
            productValidator.validate(
                brandId = brandId,
                categoryId = categoryId
            )
        }
    }
}
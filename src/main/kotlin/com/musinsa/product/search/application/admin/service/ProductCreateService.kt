package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.CreateRequest
import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.ProductCreateFailedException
import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.Response
import com.musinsa.product.search.domain.Product
import com.musinsa.product.search.domain.ProductPrice
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ProductCreateService(
    private val brandRepository: com.musinsa.product.search.application.admin.port.out.BrandRepository,
    private val categoryRepository: com.musinsa.product.search.application.admin.port.out.CategoryRepository,
    private val productRepository: com.musinsa.product.search.application.admin.port.out.ProductRepository
) : com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase {
    override fun create(request: CreateRequest): Response {
        request.validate()

        val product = try {
            with(request) {
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
        } catch (e: Exception) {
            throw ProductCreateFailedException(e)
        }

        val savedProduct = productRepository.save(product)

        return Response(
            savedProduct.id!!
        )
    }

    private fun CreateRequest.validate() {
        if (!brandRepository.existsById(brandId)) throw com.musinsa.product.search.application.exception.BrandNotFoundException()
        if (!categoryRepository.existsById(categoryId)) throw com.musinsa.product.search.application.exception.CategoryNotFoundException()
    }
}
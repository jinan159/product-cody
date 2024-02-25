package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase
import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.CreateRequest
import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.ProductCreateFailedException
import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.Response
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.application.admin.port.out.CategoryRepository
import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.application.exception.BrandNotFoundException
import com.musinsa.product.search.application.exception.CategoryNotFoundException
import com.musinsa.product.search.domain.Product
import com.musinsa.product.search.domain.ProductPrice
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ProductCreateService(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ProductCreateUseCase {
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
        if (!brandRepository.existsById(brandId)) throw BrandNotFoundException()
        if (!categoryRepository.existsById(categoryId)) throw CategoryNotFoundException()
    }
}
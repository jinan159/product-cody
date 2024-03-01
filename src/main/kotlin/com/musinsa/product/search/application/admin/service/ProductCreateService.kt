package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase
import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.CreateRequest
import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.ProductCreateFailedException
import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.Response
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.application.admin.port.out.CategoryRepository
import com.musinsa.product.search.application.admin.port.out.ProductChangedEventPublisher
import com.musinsa.product.search.application.admin.port.out.ProductChangedEventPublisher.EventPayload
import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.application.exception.BrandNotFoundException
import com.musinsa.product.search.application.exception.CategoryNotFoundException
import com.musinsa.product.search.domain.Product
import com.musinsa.product.search.domain.ProductPrice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductCreateService(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val productChangedEventPublisher: ProductChangedEventPublisher
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
                        amount = amount
                    )
                )
            }
        } catch (e: Exception) {
            throw ProductCreateFailedException(e)
        }

        val savedProduct = productRepository.save(product)

        productChangedEventPublisher.publish(
            EventPayload(savedProduct.id!!)
        )

        return Response(
            savedProduct.id
        )
    }

    private fun CreateRequest.validate() {
        if (!brandRepository.existsById(brandId)) throw BrandNotFoundException()
        if (!categoryRepository.existsById(categoryId)) throw CategoryNotFoundException()
    }
}
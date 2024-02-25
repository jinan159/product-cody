package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.ProductDeleteUseCase
import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.application.exception.ProductNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ProductDeleteService(
    private val productRepository: ProductRepository
) : ProductDeleteUseCase {
    override fun delete(id: Long) {
        val product = productRepository.findById(id)
            ?: throw ProductNotFoundException()

        productRepository.delete(product)
    }
}
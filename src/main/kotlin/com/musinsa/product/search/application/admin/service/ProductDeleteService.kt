package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.ProductDeleteUseCase
import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.application.exception.ProductNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ProductDeleteService(
    private val productRepository: com.musinsa.product.search.application.admin.port.out.ProductRepository
) : com.musinsa.product.search.application.admin.port.`in`.ProductDeleteUseCase {
    override fun delete(id: Long) {
        val product = productRepository.findById(id)
            ?: throw com.musinsa.product.search.application.exception.ProductNotFoundException()

        productRepository.delete(product)
    }
}
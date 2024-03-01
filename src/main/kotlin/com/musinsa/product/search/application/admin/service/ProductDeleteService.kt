package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.ProductDeleteUseCase
import com.musinsa.product.search.application.admin.port.out.ProductChangedEventPublisher
import com.musinsa.product.search.application.admin.port.out.ProductChangedEventPublisher.EventPayload
import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.application.exception.ProductNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductDeleteService(
    private val productRepository: ProductRepository,
    private val productChangedEventPublisher: ProductChangedEventPublisher
) : ProductDeleteUseCase {
    override fun delete(id: Long) {
        val product = productRepository.findById(id)
            ?: throw ProductNotFoundException()

        productRepository.delete(product)

        productChangedEventPublisher.publish(
            EventPayload(product.id!!)
        )
    }
}
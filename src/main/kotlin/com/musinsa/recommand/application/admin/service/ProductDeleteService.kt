package com.musinsa.recommand.application.admin.service

import com.musinsa.recommand.application.admin.port.`in`.ProductDeleteUseCase
import com.musinsa.recommand.application.admin.port.out.ProductRepository
import com.musinsa.recommand.application.exception.ProductNotFoundException

class ProductDeleteService(
    private val productRepository: ProductRepository
) : ProductDeleteUseCase {
    override fun delete(id: Long) {
        if (!productRepository.existsById(id)) throw ProductNotFoundException()

        productRepository.delete(id = id)
    }
}
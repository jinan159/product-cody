package com.musinsa.product.cody.application.admin.port.out

import com.musinsa.product.cody.domain.Product

interface ProductRepository {
    fun save(product: Product): Product
    fun findById(id: Long): Product?
    fun delete(product: Product)
    fun existsById(id: Long): Boolean

}

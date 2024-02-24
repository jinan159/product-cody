package com.musinsa.product.search.application.admin.port.out

import com.musinsa.product.search.domain.Product

interface ProductRepository {
    fun save(product: Product): Product
    fun findById(id: Long): Product?
    fun delete(product: Product)
    fun existsById(id: Long): Boolean

}

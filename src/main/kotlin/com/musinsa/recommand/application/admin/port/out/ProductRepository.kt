package com.musinsa.recommand.application.admin.port.out

import com.musinsa.recommand.domain.Product

interface ProductRepository {
    fun save(product: Product): Product
    fun findById(productId: Long): Product?
    fun delete(id: Long)
    fun existsById(id: Long): Boolean

}

package com.musinsa.product.search.application.admin.port.out

import com.musinsa.product.search.domain.Brand

interface BrandRepository {
    fun save(brand: com.musinsa.product.search.domain.Brand): com.musinsa.product.search.domain.Brand
    fun existsById(id: Long): Boolean
    fun findById(id: Long): com.musinsa.product.search.domain.Brand?
}
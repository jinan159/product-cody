package com.musinsa.product.cody.application.admin.port.out

import com.musinsa.product.cody.domain.Brand

interface BrandRepository {
    fun save(brand: Brand): Brand
    fun existsById(id: Long): Boolean
    fun findById(id: Long): Brand?
}
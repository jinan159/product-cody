package com.musinsa.recommand.application.admin.port.out

import com.musinsa.recommand.domain.Brand

interface BrandRepository {
    fun save(brand: Brand): Brand
    fun existsById(brandId: Long): Boolean
}
package com.musinsa.recommand.application.admin.service

import com.musinsa.recommand.application.admin.port.out.BrandRepository
import com.musinsa.recommand.application.admin.port.out.CategoryRepository
import com.musinsa.recommand.application.exception.BrandNotFoundException
import com.musinsa.recommand.application.exception.CategoryNotFoundException
import java.math.BigDecimal

class ProductValidator(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
) {
    fun validate(
        brandId: Long?,
        categoryId: Long?
    ) {
        if (brandId != null && !brandRepository.existsById(brandId)) throw BrandNotFoundException()
        if (categoryId != null && !categoryRepository.existsById(categoryId)) throw CategoryNotFoundException()
    }
}
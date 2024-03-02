package com.musinsa.product.cody.application.admin.port.out

import com.musinsa.product.cody.domain.Category

interface CategoryRepository {
    fun existsById(id: Long): Boolean
    fun save(category: Category): Category
    fun findById(id: Long): Category?

}

package com.musinsa.product.search.application.admin.port.out

interface CategoryRepository {
    fun existsById(id: Long): Boolean

}

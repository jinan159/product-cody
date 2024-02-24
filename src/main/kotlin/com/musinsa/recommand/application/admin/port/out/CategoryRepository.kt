package com.musinsa.recommand.application.admin.port.out

interface CategoryRepository {
    fun existsById(categoryId: Long): Boolean

}

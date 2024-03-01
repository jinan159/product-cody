package com.musinsa.product.cody.application.admin.port.out

interface CategoryRepository {
    fun existsById(id: Long): Boolean

}

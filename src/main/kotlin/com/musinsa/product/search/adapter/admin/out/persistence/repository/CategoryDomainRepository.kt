package com.musinsa.product.search.adapter.admin.out.persistence.repository

import com.musinsa.product.search.adapter.admin.out.persistence.entity.CategoryEntity
import com.musinsa.product.search.application.admin.port.out.CategoryRepository
import org.springframework.stereotype.Repository

@Repository
class CategoryDomainRepository : CategoryRepository {
    override fun existsById(id: Long): Boolean {
        return CategoryEntity.findById(id) != null
    }
}
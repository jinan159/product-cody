package com.musinsa.product.cody.adapter.admin.out.persistence.repository

import com.musinsa.product.cody.adapter.admin.out.persistence.entity.CategoryEntity
import com.musinsa.product.cody.application.admin.port.out.CategoryRepository
import org.springframework.stereotype.Repository

@Repository
class CategoryDomainRepository : CategoryRepository {
    override fun existsById(id: Long): Boolean {
        return CategoryEntity.findById(id) != null
    }
}
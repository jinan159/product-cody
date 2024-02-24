package com.musinsa.product.search.adapter.admin.out.persistence.repository

import com.musinsa.product.search.application.admin.port.out.CategoryRepository
import org.springframework.stereotype.Repository

@Repository
class CategoryDomainRepository(
    private val categoryJpaRepository: com.musinsa.product.search.adapter.admin.out.persistence.repository.CategoryJpaRepository
) : com.musinsa.product.search.application.admin.port.out.CategoryRepository {
    override fun existsById(id: Long): Boolean {
        return categoryJpaRepository.existsById(id)
    }
}
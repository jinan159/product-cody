package com.musinsa.product.search.adapter.admin.out.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.musinsa.product.search.adapter.admin.out.persistence.entity.CategoryEntity

interface CategoryJpaRepository : JpaRepository<CategoryEntity, Long> {
}
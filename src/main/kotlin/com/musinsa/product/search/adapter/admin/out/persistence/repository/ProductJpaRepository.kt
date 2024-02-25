package com.musinsa.product.search.adapter.admin.out.persistence.repository

import com.musinsa.product.search.adapter.admin.out.persistence.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<ProductEntity, Long> {
}
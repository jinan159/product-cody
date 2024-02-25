package com.musinsa.product.search.adapter.admin.out.persistence.repository

import com.musinsa.product.search.adapter.admin.out.persistence.entity.BrandEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BrandJpaRepository : JpaRepository<BrandEntity, Long> {
}
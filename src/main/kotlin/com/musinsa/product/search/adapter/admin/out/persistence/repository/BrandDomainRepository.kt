package com.musinsa.product.search.adapter.admin.out.persistence.repository

import com.musinsa.product.search.adapter.admin.out.persistence.entity.BrandEntity
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.domain.Brand
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class BrandDomainRepository(
    private val brandJpaRepository: com.musinsa.product.search.adapter.admin.out.persistence.repository.BrandJpaRepository
) : com.musinsa.product.search.application.admin.port.out.BrandRepository {
    override fun save(brand: com.musinsa.product.search.domain.Brand): com.musinsa.product.search.domain.Brand {
        return brandJpaRepository.save(brand.toEntity())
            .toDomain()
    }

    override fun existsById(id: Long): Boolean {
        return brandJpaRepository.existsById(id)
    }

    override fun findById(id: Long): com.musinsa.product.search.domain.Brand? {
        return brandJpaRepository.findById(id)
            .getOrNull()
            ?.toDomain()
    }

    private fun com.musinsa.product.search.domain.Brand.toEntity(): com.musinsa.product.search.adapter.admin.out.persistence.entity.BrandEntity {
        return with(this) {
            com.musinsa.product.search.adapter.admin.out.persistence.entity.BrandEntity(
                id = id,
                name = name
            )
        }
    }

    private fun com.musinsa.product.search.adapter.admin.out.persistence.entity.BrandEntity.toDomain(): com.musinsa.product.search.domain.Brand {
        return with(this) {
            com.musinsa.product.search.domain.Brand(
                id = id,
                name = name
            )
        }
    }
}
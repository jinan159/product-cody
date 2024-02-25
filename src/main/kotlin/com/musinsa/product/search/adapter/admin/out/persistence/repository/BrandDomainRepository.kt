package com.musinsa.product.search.adapter.admin.out.persistence.repository

import com.musinsa.product.search.adapter.admin.out.persistence.entity.BrandEntity
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.domain.Brand
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class BrandDomainRepository(
    private val brandJpaRepository: BrandJpaRepository
) : BrandRepository {
    override fun save(brand: Brand): Brand {
        return brandJpaRepository.save(brand.toEntity())
            .toDomain()
    }

    override fun existsById(id: Long): Boolean {
        return brandJpaRepository.existsById(id)
    }

    override fun findById(id: Long): Brand? {
        return brandJpaRepository.findById(id)
            .getOrNull()
            ?.toDomain()
    }

    private fun Brand.toEntity(): BrandEntity {
        return with(this) {
            BrandEntity(
                id = id,
                name = name
            )
        }
    }

    private fun BrandEntity.toDomain(): Brand {
        return with(this) {
            Brand(
                id = id,
                name = name
            )
        }
    }
}
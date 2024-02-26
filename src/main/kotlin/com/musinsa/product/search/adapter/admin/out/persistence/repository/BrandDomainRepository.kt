package com.musinsa.product.search.adapter.admin.out.persistence.repository

import com.musinsa.product.search.adapter.admin.out.persistence.entity.BrandEntity
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.domain.Brand
import org.springframework.stereotype.Repository

@Repository
class BrandDomainRepository : BrandRepository {
    override fun save(brand: Brand): Brand {
        if (brand.id == null) {
            return insert(brand)
        }

        return BrandEntity.findById(brand.id)
            ?.update(brand)
            ?: return insert(brand)
    }

    override fun existsById(id: Long): Boolean {
        return BrandEntity.findById(id) != null
    }

    override fun findById(id: Long): Brand? {
        return BrandEntity.findById(id)
            ?.toDomain()
    }

    private fun insert(brand: Brand): Brand {
        return BrandEntity.new {
            name = brand.name
        }
            .toDomain()
    }

    private fun BrandEntity.update(brand: Brand): Brand {
        this.name = brand.name

        return this.toDomain()
    }

    private fun BrandEntity.toDomain(): Brand {
        return with(this) {
            Brand(
                id = id.value,
                name = name
            )
        }
    }
}
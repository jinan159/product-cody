package com.musinsa.product.cody.adapter.admin.out.persistence.repository

import com.musinsa.product.cody.adapter.admin.out.persistence.entity.BrandEntity
import com.musinsa.product.cody.adapter.admin.out.persistence.entity.CategoryEntity
import com.musinsa.product.cody.application.admin.port.out.CategoryRepository
import com.musinsa.product.cody.domain.Brand
import com.musinsa.product.cody.domain.Category
import org.springframework.stereotype.Repository

@Repository
class CategoryDomainRepository : CategoryRepository {
    override fun existsById(id: Long): Boolean {
        return CategoryEntity.findById(id) != null
    }

    override fun save(category: Category): Category {
        if (category.id == null) {
            return insert(category)
        }

        return CategoryEntity.findById(category.id)
            ?.update(category)
            ?: return insert(category)
    }

    override fun findById(id: Long): Category? {
        return CategoryEntity.findById(id)
            ?.toDomain()
    }

    private fun insert(category: Category): Category {
        return CategoryEntity.new {
            name = category.name
        }
            .toDomain()
    }

    private fun CategoryEntity.update(category: Category): Category {
        this.name = category.name

        return this.toDomain()
    }

    private fun CategoryEntity.toDomain(): Category {
        return with(this) {
            Category(
                id = id.value,
                name = name
            )
        }
    }
}
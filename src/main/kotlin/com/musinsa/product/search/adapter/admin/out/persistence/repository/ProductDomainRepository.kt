package com.musinsa.product.search.adapter.admin.out.persistence.repository

import com.musinsa.product.search.adapter.admin.out.persistence.entity.BrandEntity
import com.musinsa.product.search.adapter.admin.out.persistence.entity.ProductEntity
import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.domain.Brand
import com.musinsa.product.search.domain.Product
import com.musinsa.product.search.domain.ProductPrice
import org.springframework.stereotype.Repository

@Repository
class ProductDomainRepository : ProductRepository {
    override fun save(product: Product): Product {
        if (product.id == null) {
            return insert(product)
        }

        return ProductEntity.findById(product.id)
            ?.update(product)
            ?: return insert(product)
    }

    override fun findById(id: Long): Product? {
        return ProductEntity.findById(id)
            ?.toDomain()
    }

    override fun delete(product: Product) {
        if (product.id != null) {
            ProductEntity.findById(product.id)
                ?.delete()
        }
    }

    override fun existsById(id: Long): Boolean {
        return ProductEntity.findById(id) != null
    }

    private fun insert(product: Product): Product {
        return ProductEntity.new {
            categoryId = product.categoryId
            brandId = product.brandId
            name = product.name
            amount = product.price.amount
        }
            .toDomain()
    }

    private fun ProductEntity.update(product: Product): Product {
        this.categoryId = product.categoryId
        this.brandId = product.brandId
        this.name = product.name
        this.amount = product.price.amount

        return this.toDomain()
    }

    private fun ProductEntity.toDomain(): Product {
        return with(this) {
            Product(
                id = id.value,
                categoryId = categoryId,
                brandId = brandId,
                name = name,
                price = ProductPrice(
                    amount = amount,
                ),
            )
        }
    }
}
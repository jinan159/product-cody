package com.musinsa.product.search.adapter.admin.out.persistence.repository

import com.musinsa.product.search.domain.Product
import com.musinsa.product.search.domain.ProductPrice
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductDomainRepository(
    private val productJpaRepository: com.musinsa.product.search.adapter.admin.out.persistence.repository.ProductJpaRepository
) : com.musinsa.product.search.application.admin.port.out.ProductRepository {
    override fun save(product: Product): Product {
        return productJpaRepository.save(product.toEntity())
            .toDomain()
    }

    override fun findById(id: Long): Product? {
        return productJpaRepository.findById(id)
            .getOrNull()
            ?.toDomain()
    }

    override fun delete(product: Product) {
        productJpaRepository.delete(product.toEntity())
    }

    override fun existsById(id: Long): Boolean {
        return productJpaRepository.existsById(id)
    }

    private fun Product.toEntity(): com.musinsa.product.search.adapter.admin.out.persistence.entity.ProductEntity {
        return with(this) {
            com.musinsa.product.search.adapter.admin.out.persistence.entity.ProductEntity(
                id = id,
                categoryId = categoryId,
                brandId = brandId,
                name = name,
                currency = price.currency,
                amount = price.amount,
            )
        }
    }

    private fun com.musinsa.product.search.adapter.admin.out.persistence.entity.ProductEntity.toDomain(): Product {
        return with(this) {
            Product(
                id = id,
                categoryId = categoryId,
                brandId = brandId,
                name = name,
                price = ProductPrice(
                    currency = currency,
                    amount = amount,
                ),
            )
        }
    }
}
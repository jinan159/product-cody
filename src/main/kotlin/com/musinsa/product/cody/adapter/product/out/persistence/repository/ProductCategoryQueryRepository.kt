package com.musinsa.product.cody.adapter.product.out.persistence.repository

import com.musinsa.product.cody.adapter.out.persistence.table.Categories
import com.musinsa.product.cody.adapter.out.persistence.table.Products
import com.musinsa.product.cody.application.product.port.out.ProductCategoryRepository
import com.musinsa.product.cody.application.product.port.out.ProductCategoryRepository.ProductCategory
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Repository
class ProductCategoryQueryRepository : ProductCategoryRepository {
    override fun findById(id: Long): ProductCategory? {
        return Products.join(
            otherTable = Categories,
            joinType = JoinType.INNER,
            onColumn = Products.categoryId,
            otherColumn = Categories.id
        )
            .slice(
                Products.id,
                Categories.name
            )
            .select {
                Products.id eq id
            }
            .firstOrNull()
            ?.let {
                ProductCategory(
                    productId = it[Products.id].value,
                    categoryName = it[Categories.name]
                )
            }
    }
}
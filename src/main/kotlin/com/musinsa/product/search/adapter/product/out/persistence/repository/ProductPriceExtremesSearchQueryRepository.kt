package com.musinsa.product.search.adapter.product.out.persistence.repository

import com.musinsa.product.search.adapter.out.persistence.table.Brands
import com.musinsa.product.search.adapter.out.persistence.table.Categories
import com.musinsa.product.search.adapter.out.persistence.table.Products
import com.musinsa.product.search.application.product.port.out.ProductPriceExtremesSearchRepository
import com.musinsa.product.search.application.product.port.out.ProductPriceExtremesSearchRepository.CategorySearchResult
import com.musinsa.product.search.application.product.port.out.ProductPriceExtremesSearchRepository.Item
import com.musinsa.product.search.application.product.port.out.ProductPriceExtremesSearchRepository.SearchResult
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.max
import org.jetbrains.exposed.sql.min
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository

@Repository
class ProductPriceExtremesSearchQueryRepository : ProductPriceExtremesSearchRepository {
    override fun searchCategoryIdByCategoryName(categoryName: String): CategorySearchResult? {
        return Categories.select {
            Categories.name eq categoryName
        }
            .firstOrNull()
            ?.let {
                CategorySearchResult(
                    id = it[Categories.id].value,
                    name = it[Categories.name]
                )
            }
    }

    override fun search(categoryId: Long): SearchResult? {
        val minPrice = searchQuery(
            categoryId = categoryId,
            isMinQuery = true
        ) ?: return null

        val maxPrice = searchQuery(
            categoryId = categoryId,
            isMinQuery = false
        ) ?: return null

        return SearchResult(
            minPrice = minPrice,
            maxPrice = maxPrice
        )
    }

    private fun searchQuery(categoryId: Long, isMinQuery: Boolean): Item? {
        /*
        SELECT
            priceByBrand.max_brandId,
            BRANDS."NAME",
            priceByBrand.AMOUNT
        FROM (
            SELECT
                MAX(PRODUCTS.BRAND_ID) max_brandId,
                PRODUCTS.AMOUNT
            FROM PRODUCTS
            INNER JOIN (
                SELECT
                    PRODUCTS.CATEGORY_ID categoryId,
                    MIN(PRODUCTS.AMOUNT) min_amount     /* MIN(PRODUCTS.AMOUNT) or MAX(PRODUCTS.AMOUNT) */
                FROM PRODUCTS
                GROUP BY PRODUCTS.CATEGORY_ID
                HAVING PRODUCTS.CATEGORY_ID = 1
            ) priceByCategory ON PRODUCTS.CATEGORY_ID = priceByCategory.categoryId
                AND (PRODUCTS.AMOUNT = priceByCategory.min_amount)
            GROUP BY PRODUCTS.AMOUNT
        ) priceByBrand
        INNER JOIN BRANDS ON priceByBrand.max_brandId = BRANDS.ID
        LIMIT 1
         */

        val priceByCategory_categoryId = Products.categoryId.alias("categoryId")
        val priceByCategory_amount = when (isMinQuery) {
            true -> Products.amount.min().alias("min_amount")
            false -> Products.amount.max().alias("max_amount")
        }
        val priceByCategory = Products
            .slice(
                priceByCategory_categoryId,
                priceByCategory_amount
            )
            .selectAll()
            .groupBy(
                Products.categoryId
            )
            .having {
                Products.categoryId eq categoryId
            }
            .alias("priceByCategory")

        val priceByBrand_max_brandId = Products.brandId.max().alias("max_brandId")
        val priceByBrand_amount = Products.amount
        val priceByBrand = Products
            .join(
                otherTable = priceByCategory,
                joinType = JoinType.INNER,
                onColumn = Products.categoryId,
                otherColumn = priceByCategory[priceByCategory_categoryId],
                additionalConstraint = {
                    Products.amount eq priceByCategory[priceByCategory_amount]
                }
            )
            .slice(
                priceByBrand_max_brandId,
                priceByBrand_amount
            )
            .selectAll()
            .groupBy(priceByBrand_amount)
            .alias("priceByBrand")

        return priceByBrand
            .join(
                otherTable = Brands,
                joinType = JoinType.INNER,
                onColumn = priceByBrand[priceByBrand_max_brandId],
                otherColumn = Brands.id
            )
            .slice(
                priceByBrand[priceByBrand_max_brandId],
                Brands.name,
                priceByBrand[priceByBrand_amount]
            )
            .selectAll()
            .limit(1)
            .firstOrNull()
            ?.let {
                Item(
                    brandName = it[Brands.name],
                    price = it[priceByBrand[priceByBrand_amount]]
                )
            }
    }
}
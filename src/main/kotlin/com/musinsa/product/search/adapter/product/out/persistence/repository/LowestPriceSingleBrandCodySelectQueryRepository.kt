package com.musinsa.product.search.adapter.product.out.persistence.repository

import com.musinsa.product.search.adapter.out.persistence.table.Brands
import com.musinsa.product.search.adapter.out.persistence.table.Categories
import com.musinsa.product.search.adapter.out.persistence.table.Products
import com.musinsa.product.search.application.product.port.out.LowestPriceSingleBrandCodySelectRepository
import com.musinsa.product.search.application.product.port.out.LowestPriceSingleBrandCodySelectRepository.CategoryAndPrice
import com.musinsa.product.search.application.product.port.out.LowestPriceSingleBrandCodySelectRepository.SelectResult
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder.DESC
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.castTo
import org.jetbrains.exposed.sql.min
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.sum
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class LowestPriceSingleBrandCodySelectQueryRepository : LowestPriceSingleBrandCodySelectRepository {
    override fun select(): SelectResult? {
        val lowestSingleBrand = selectLowestPriceSingleBrand()
            ?: LowestSingleBrand(
                brandId = 6L,
                brandName = "F",
                price = 0.toBigDecimal()
            )

        /*
        SELECT
            CATEGORIES."NAME",
            MIN(PRODUCTS.AMOUNT) minAmount
        FROM PRODUCTS
        INNER JOIN BRANDS ON PRODUCTS.BRAND_ID = BRANDS.ID
            AND (BRANDS.ID = ?)
        INNER JOIN CATEGORIES ON PRODUCTS.CATEGORY_ID = CATEGORIES.ID
        GROUP BY PRODUCTS.CATEGORY_ID
         */

        val minAmount = Products.amount.min().alias("minAmount")
        val rows = Products
            .join(
                otherTable = Brands,
                joinType = JoinType.INNER,
                onColumn = Products.brandId,
                otherColumn = Brands.id,
                additionalConstraint = {
                    Brands.id eq lowestSingleBrand.brandId
                }
            )
            .join(
                otherTable = Categories,
                joinType = JoinType.INNER,
                onColumn = Products.categoryId,
                otherColumn = Categories.id
            )
            .slice(
                Categories.name,
                minAmount
            )
            .selectAll()
            .groupBy(
                Products.categoryId
            )
            .toList()

        return SelectResult(
            brandName = lowestSingleBrand.brandName,
            categories = rows.map {
                CategoryAndPrice(
                    categoryName = it[Categories.name],
                    price = it[minAmount] ?: 0.toBigDecimal()
                )
            }
        )
    }

    private fun selectLowestPriceSingleBrand(): LowestSingleBrand? {
        /*
        SELECT
            minPricePerBrandCategory.brandId,
            BRANDS."NAME",
            SUM(CAST(minPricePerBrandCategory.min_amount AS DECIMAL(10, 2))) sum_amount
        FROM (
            SELECT
                PRODUCTS.BRAND_ID brandId,
                PRODUCTS.CATEGORY_ID,
                MIN(PRODUCTS.AMOUNT) min_amount
            FROM PRODUCTS
            GROUP BY brandId, PRODUCTS.CATEGORY_ID
        ) minPricePerBrandCategory
        INNER JOIN BRANDS ON minPricePerBrandCategory.brandId = BRANDS.ID
        GROUP BY minPricePerBrandCategory.brandId
        ORDER BY sum_amount LIMIT 1
         */

        val brandId = Products.brandId.alias("brandId")
        val min_amount = Products.amount.min()
            .alias("min_amount")
        val minPricePerBrandCategory = Products
            .slice(
                brandId,
                Products.categoryId,
                min_amount
            )
            .selectAll()
            .groupBy(
                brandId,
                Products.categoryId
            )
            .alias("minPricePerBrandCategory")

        val brandPriceSum = minPricePerBrandCategory[min_amount]
            .castTo<BigDecimal>(Products.amount.columnType)
            .sum()
            .alias("sum_amount")

        return minPricePerBrandCategory
            .join(
                otherTable = Brands,
                joinType = JoinType.INNER,
                onColumn = minPricePerBrandCategory[brandId],
                otherColumn = Brands.id
            )
            .slice(
                minPricePerBrandCategory[brandId],
                Brands.name,
                brandPriceSum
            )
            .selectAll()
            .groupBy(
                minPricePerBrandCategory[brandId]
            )
            .orderBy(
                brandPriceSum
            )
            .limit(1)
            .firstOrNull()
            ?.let {
                LowestSingleBrand(
                    brandId = it[minPricePerBrandCategory[brandId]],
                    brandName = it[Brands.name],
                    price = it[brandPriceSum] ?: 0.toBigDecimal()
                )
            }
    }

    private data class LowestSingleBrand(
        val brandId: Long,
        val brandName: String,
        val price: BigDecimal
    )
}
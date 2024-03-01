package com.musinsa.product.cody.adapter.product.out.persistence.repository

import com.musinsa.product.cody.adapter.out.persistence.table.Brands
import com.musinsa.product.cody.adapter.out.persistence.table.Categories
import com.musinsa.product.cody.adapter.out.persistence.table.Products
import com.musinsa.product.cody.application.product.port.out.LowestPriceCodySelectRepository
import com.musinsa.product.cody.application.product.port.out.LowestPriceCodySelectRepository.SelectResult
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.max
import org.jetbrains.exposed.sql.min
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository

@Repository
class LowestPriceCodySelectQueryRepository : LowestPriceCodySelectRepository {
    override fun select(): SelectResult {
        /*
        SELECT
            CATEGORIES."NAME",
            BRANDS."NAME",
            minPricePerBrand.min_amount
        FROM (
            SELECT
                MAX(PRODUCTS.BRAND_ID) max_brandId,
                PRODUCTS.CATEGORY_ID categoryId,
                MIN(PRODUCTS.AMOUNT) min_amount
            FROM PRODUCTS
            INNER JOIN (
                SELECT
                    PRODUCTS.CATEGORY_ID categoryId,
                    MIN(PRODUCTS.AMOUNT) min_amount
                FROM PRODUCTS
                GROUP BY PRODUCTS.CATEGORY_ID
            ) minPricePerCategory ON PRODUCTS.CATEGORY_ID = minPricePerCategory.categoryId
                AND (PRODUCTS.AMOUNT = minPricePerCategory.min_amount)
            GROUP BY PRODUCTS.CATEGORY_ID
        ) minPricePerBrand
        INNER JOIN CATEGORIES ON minPricePerBrand.categoryId = CATEGORIES.ID
        INNER JOIN BRANDS ON minPricePerBrand.max_brandId = BRANDS.ID
         */

        val minPricePerCategory_categoryId = Products.categoryId.alias("categoryId")
        val minPricePerCategory_min_amount = Products.amount.min().alias("min_amount")
        val minPricePerCategory = Products
            .slice(
                minPricePerCategory_categoryId,
                minPricePerCategory_min_amount
            )
            .selectAll()
            .groupBy(Products.categoryId)
            .alias("minPricePerCategory")

        val minPricePerBrand_max_brandId = Products.brandId.max().alias("max_brandId")
        val minPricePerBrand_categoryId = Products.categoryId.alias("categoryId")
        val minPricePerBrand_min_amount = Products.amount.min().alias("min_amount")
        val minPricePerBrand = Products
            .join(
                otherTable = minPricePerCategory,
                joinType = JoinType.INNER,
                onColumn = Products.categoryId,
                otherColumn = minPricePerCategory[minPricePerCategory_categoryId],
                additionalConstraint = {
                    Products.amount eq minPricePerCategory[minPricePerCategory_min_amount]
                }
            )
            .slice(
                minPricePerBrand_max_brandId,
                minPricePerBrand_categoryId,
                minPricePerBrand_min_amount
            )
            .selectAll()
            .groupBy(Products.categoryId)
            .alias("minPricePerBrand")

        val rows = minPricePerBrand
            .join(
                otherTable = Categories,
                joinType = JoinType.INNER,
                onColumn = minPricePerBrand[minPricePerBrand_categoryId],
                otherColumn = Categories.id,
            )
            .join(
                otherTable = Brands,
                joinType = JoinType.INNER,
                onColumn = minPricePerBrand[minPricePerBrand_max_brandId],
                otherColumn = Brands.id,
            )
            .slice(
                Categories.name,
                Brands.name,
                minPricePerBrand[minPricePerBrand_min_amount]
            )
            .selectAll()
            .toList()

        return SelectResult(
            items = rows.map {
                LowestPriceCodySelectRepository.Item(
                    categoryName = it[Categories.name],
                    brandName = it[Brands.name],
                    amount = it[minPricePerBrand[minPricePerBrand_min_amount]] ?: 0.toBigDecimal()
                )
            }
        )
    }
}
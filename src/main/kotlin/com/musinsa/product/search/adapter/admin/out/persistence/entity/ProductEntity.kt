package com.musinsa.product.search.adapter.admin.out.persistence.entity

import com.musinsa.product.search.adapter.out.persistence.table.Products
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProductEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ProductEntity>(Products)
    var categoryId by Products.categoryId
    var brandId by Products.brandId
    var name by Products.name
    var amount by Products.amount
}
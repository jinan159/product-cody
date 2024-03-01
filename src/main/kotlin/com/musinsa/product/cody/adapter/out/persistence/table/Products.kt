package com.musinsa.product.cody.adapter.out.persistence.table

import org.jetbrains.exposed.dao.id.LongIdTable

object Products : LongIdTable("products") {
    val categoryId = long("category_id")
    val brandId = long("brand_id")
    val name = varchar("name", 255)
    val amount = decimal("amount", 10, 2)
}
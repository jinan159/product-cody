package com.musinsa.product.search.adapter.admin.out.persistence.entity

import com.musinsa.product.search.adapter.out.persistence.table.Categories
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CategoryEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CategoryEntity>(Categories)
    var name by Categories.name
}
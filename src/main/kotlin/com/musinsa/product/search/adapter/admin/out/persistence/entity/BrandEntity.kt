package com.musinsa.product.search.adapter.admin.out.persistence.entity

import com.musinsa.product.search.adapter.out.persistence.table.Brands
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class BrandEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<BrandEntity>(Brands)
    var name by Brands.name
}
package com.musinsa.product.cody.adapter.out.persistence.table

import org.jetbrains.exposed.dao.id.LongIdTable

object Categories : LongIdTable("categories") {
    val name = varchar("name", 255)
}
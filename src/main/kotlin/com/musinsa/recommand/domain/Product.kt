package com.musinsa.recommand.domain

import java.math.BigDecimal
import java.util.Currency

class Product(
    id: Long,
    val brandId: Long,
    val categoryId: Long,
    val name: String,
    val price: ProductPrice
)

class ProductPrice(
    val currency: Currency = Currency.getInstance("KRW"),
    val amount: BigDecimal
)
package com.musinsa.recommand.domain

import java.math.BigDecimal
import java.util.Currency

class Product(
    val id: Long? = null,
    brandId: Long,
    categoryId: Long,
    name: String,
    price: ProductPrice
) {
    var brandId: Long = brandId
        private set
    var categoryId: Long = categoryId
        private set
    var name: String = name
        private set
    var price: ProductPrice = price
        private set

    fun update(
        brandId: Long? = null,
        categoryId: Long? = null,
        name: String? = null,
        currency: Currency? = null,
        amount: BigDecimal? = null
    ) {
        if (brandId != null) this.brandId = brandId
        if (categoryId != null) this.categoryId = categoryId
        if (name != null) this.name = name
        if (currency != null || amount != null) {
            this.price.update(
                currency = currency,
                amount = amount
            )
        }
    }
}

class ProductPrice(
    currency: Currency = Currency.getInstance("KRW"),
    amount: BigDecimal
) {
    init {
        validateAmount(amount)
    }

    var currency: Currency = currency
        private set
    var amount: BigDecimal = amount
        private set(value) {
            validateAmount(value)

            field = value
        }

    private fun validateAmount(value: BigDecimal) {
        require(0.toBigDecimal() <= value) {
            "상품 가격은 0 이상이어야 합니다."
        }
    }

    fun update(
        currency: Currency?,
        amount: BigDecimal?
    ) {
        if (currency != null) this.currency = currency
        if (amount != null) this.amount = amount
    }
}
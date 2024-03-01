package com.musinsa.product.cody.domain

import java.math.BigDecimal

class Product(
    val id: Long? = null,
    categoryId: Long,
    brandId: Long,
    name: String,
    price: ProductPrice
) {
    var categoryId: Long = categoryId
        private set
    var brandId: Long = brandId
        private set
    var name: String = name
        private set
    var price: ProductPrice = price
        private set

    fun update(
        brandId: Long? = null,
        categoryId: Long? = null,
        name: String? = null,
        amount: BigDecimal? = null
    ) {
        if (brandId != null) this.brandId = brandId
        if (categoryId != null) this.categoryId = categoryId
        if (name != null) this.name = name
        if (amount != null) {
            this.price.update(
                amount = amount
            )
        }
    }
}

class ProductPrice(
    amount: BigDecimal
) {
    init {
        validateAmount(amount)
    }
    var amount: BigDecimal = amount
        private set(value) {
            validateAmount(value)

            field = value
        }

    private fun validateAmount(value: BigDecimal) {
        require(0.toBigDecimal() <= value) {
            "상품 가격은 0 이상이어야 합니다"
        }
    }

    fun update(
        amount: BigDecimal
    ) {
        this.amount = amount
    }
}
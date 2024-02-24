package com.musinsa.recommand.domain

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import java.util.Currency

class ProductTest : ShouldSpec({
    should("상품 생성에 성공한다") {
        // given
        val brandId = 1L
        val categoryId = 2L
        val name = "test-product"
        val currency = Currency.getInstance("KRW")
        val amount = 1000.toBigDecimal()

        // when
        val product = Product(
            brandId = brandId,
            categoryId = categoryId,
            name = name,
            price = ProductPrice(
                currency = currency,
                amount = amount
            )
        )

        // then
        product.brandId shouldBe brandId
        product.categoryId shouldBe categoryId
        product.name shouldBe name
        product.price.currency shouldBe currency
        product.price.amount shouldBe amount
    }

    context("상품을 생성할 때") {
        should("가격이 0 미만이면 예외가 발생한다") {
            // given
            val brandId = 1L
            val categoryId = 2L
            val name = "test-product"
            val currency = Currency.getInstance("KRW")
            val amount = (-1000).toBigDecimal()

            // when
            val exception = shouldThrowAny {
                Product(
                    brandId = brandId,
                    categoryId = categoryId,
                    name = name,
                    price = ProductPrice(
                        currency = currency,
                        amount = amount
                    )
                )
            }

            // then
            exception.shouldBeTypeOf<IllegalArgumentException>()
        }
    }

    context("상품을 수정할 때") {
       should("가격이 0 미만이면 예외가 발생한다") {
            // given
           val product = Product(
               brandId = 1L,
               categoryId = 2L,
               name = "test-product",
               price = ProductPrice(
                   currency = Currency.getInstance("KRW"),
                   amount = 1000.toBigDecimal()
               )
           )

           // when
           val exception = shouldThrowAny {
               product.update(
                   amount = (-1).toBigDecimal()
               )
           }

           // then
           exception.shouldBeTypeOf<IllegalArgumentException>()
       }
    }
})
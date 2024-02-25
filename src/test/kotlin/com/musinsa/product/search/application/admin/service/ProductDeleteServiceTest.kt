package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.application.exception.ProductNotFoundException
import com.musinsa.product.search.domain.Product
import com.musinsa.product.search.domain.ProductPrice
import com.musinsa.product.search.testsupport.ServiceShouldSpec
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeTypeOf
import java.util.Currency

class ProductDeleteServiceTest(
    private val productRepository: ProductRepository
) : ServiceShouldSpec({
    val service = ProductDeleteService(
        productRepository = productRepository
    )

    should("상품 삭제를 성공한다") {
        // given
        val savedProduct = productRepository.save(
            Product(
                brandId = 0L,
                categoryId = 1L,
                name = "test-product-name",
                price = ProductPrice(
                    currency = Currency.getInstance("KRW"),
                    amount = 1000.toBigDecimal()
                )
            )
        )

        // when
        service.delete(savedProduct.id!!)
        val productAfterDelete = productRepository.findById(savedProduct.id!!)

        // then
        productAfterDelete.shouldBeNull()
    }

    should("상품을 삭제할 때 존재하지 않는 상품이면 예외가 발생한다") {
        // given
        val id = Long.MIN_VALUE

        // then
        val exception = shouldThrowAny {
            service.delete(id)
        }

        // then
        exception.shouldBeTypeOf<ProductNotFoundException>()
    }
})
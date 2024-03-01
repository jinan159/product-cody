package com.musinsa.product.cody.application.admin.service

import com.musinsa.product.cody.application.admin.port.out.ProductChangedEventPublisher
import com.musinsa.product.cody.application.admin.port.out.ProductRepository
import com.musinsa.product.cody.application.exception.ProductNotFoundException
import com.musinsa.product.cody.domain.Product
import com.musinsa.product.cody.domain.ProductPrice
import com.musinsa.product.cody.testsupport.ServiceShouldSpec
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.transaction.annotation.Transactional

@Transactional
class ProductDeleteServiceTest(
    private val productRepository: ProductRepository
) : ServiceShouldSpec({
    val eventPublisher = mockk<ProductChangedEventPublisher>()
    val service = ProductDeleteService(
        productRepository = productRepository,
        productChangedEventPublisher = eventPublisher
    )

    beforeTest {
        clearMocks(eventPublisher)
    }

    should("상품 삭제를 성공한다") {
        // given
        every { eventPublisher.publish(any()) } returns Unit

        val savedProduct = productRepository.save(
            Product(
                brandId = 0L,
                categoryId = 1L,
                name = "test-product-name",
                price = ProductPrice(
                    amount = 1000.toBigDecimal()
                )
            )
        )

        // when
        service.delete(savedProduct.id!!)
        val productAfterDelete = productRepository.findById(savedProduct.id!!)

        // then
        productAfterDelete.shouldBeNull()

        verify(exactly = 1) {
            eventPublisher.publish(any())
        }
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

        verify(exactly = 0) {
            eventPublisher.publish(any())
        }
    }
})
package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.ProductUpdateUseCase.ProductUpdateFailedException
import com.musinsa.product.search.application.admin.port.`in`.ProductUpdateUseCase.UpdateRequest
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.application.admin.port.out.CategoryRepository
import com.musinsa.product.search.application.admin.port.out.ProductChangedEventPublisher
import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.application.exception.BrandNotFoundException
import com.musinsa.product.search.application.exception.CategoryNotFoundException
import com.musinsa.product.search.application.exception.ProductNotFoundException
import com.musinsa.product.search.domain.Product
import com.musinsa.product.search.domain.ProductPrice
import com.musinsa.product.search.testsupport.ServiceShouldSpec
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Transactional
class ProductUpdateServiceTest(
    private val productRepository: ProductRepository
) : ServiceShouldSpec({
    val brandRepository = mockk<BrandRepository>()
    val categoryRepository = mockk<CategoryRepository>()
    val eventPublisher = spyk<ProductChangedEventPublisher> {
        every { publish(any()) } returns Unit
    }
    val service = ProductUpdateService(
        brandRepository = brandRepository,
        categoryRepository = categoryRepository,
        productRepository = productRepository,
        productChangedEventPublisher = eventPublisher
    )

    val product = Product(
        brandId = 0L,
        categoryId = 1L,
        name = "test-product-name",
        price = ProductPrice(
            amount = 1000.toBigDecimal()
        )
    )

    fun createUpdateRequest(
        id: Long,
        amount: BigDecimal = 2000.toBigDecimal()
    ): UpdateRequest
        = UpdateRequest(
            id = id,
            brandId = 2L,
            categoryId = 3L,
            name = "test-updated-product-name",
            amount = amount
        )

    beforeTest {
        clearMocks(
            brandRepository,
            categoryRepository,
            eventPublisher
        )
    }

    should("상품 수정을 성공한다") {
        // given
        every { brandRepository.existsById(any()) } returns true
        every { categoryRepository.existsById(any()) } returns true
        val savedProduct = productRepository.save(product)
        val updateRequest = createUpdateRequest(id = savedProduct.id!!)

        // when
        service.update(updateRequest)
        val productAfterUpdate = productRepository.findById(savedProduct.id!!)

        // then
        productAfterUpdate.shouldNotBeNull()
        productAfterUpdate.id shouldBe savedProduct.id
        with(updateRequest) {
            productAfterUpdate.brandId shouldBe brandId
            productAfterUpdate.categoryId shouldBe categoryId
            productAfterUpdate.name shouldBe name
            productAfterUpdate.price.amount.compareTo(amount) shouldBe 0
        }

        verify(exactly = 1) {
            eventPublisher.publish(any())
        }
    }

    context("상품을 수정할 때") {
        should("브랜드가 존재하지 않으면 예외가 발생한다") {
            // given
            every { brandRepository.existsById(any()) } returns false
            val savedProduct = productRepository.save(product)
            val updateRequest = createUpdateRequest(id = savedProduct.id!!)

            // when
            val exception = shouldThrowAny {
                service.update(updateRequest)
            }

            // then
            exception.shouldBeTypeOf<BrandNotFoundException>()

            verify(exactly = 0) {
                eventPublisher.publish(any())
            }
        }

        should("카테고리가 존재하지 않으면 예외가 발생한다") {
            every { brandRepository.existsById(any()) } returns true
            every { categoryRepository.existsById(any()) } returns false
            val savedProduct = productRepository.save(product)
            val updateRequest = createUpdateRequest(id = savedProduct.id!!)

            // when
            val exception = shouldThrowAny {
                service.update(updateRequest)
            }

            // then
            exception.shouldBeTypeOf<CategoryNotFoundException>()

            verify(exactly = 0) {
                eventPublisher.publish(any())
            }
        }

        should("존재하지 않는 상품이면 예외가 발생한다") {
            every { brandRepository.existsById(any()) } returns true
            every { categoryRepository.existsById(any()) } returns true
            val notExistingProductId = Long.MIN_VALUE
            val updateRequest = createUpdateRequest(id = notExistingProductId)

            // when
            val exception = shouldThrowAny {
                service.update(updateRequest)
            }

            // then
            exception.shouldBeTypeOf<ProductNotFoundException>()

            verify(exactly = 0) {
                eventPublisher.publish(any())
            }
        }

        should("상품 수정에 싪패하면 예외가 발생한다") {
            every { brandRepository.existsById(any()) } returns true
            every { categoryRepository.existsById(any()) } returns true
            val savedProduct = productRepository.save(product)
            val updateRequest = createUpdateRequest(
                id = savedProduct.id!!,
                amount = (-1000).toBigDecimal()
            )

            // when
            val exception = shouldThrowAny {
                service.update(updateRequest)
            }

            // then
            exception.shouldBeTypeOf<ProductUpdateFailedException>()

            verify(exactly = 0) {
                eventPublisher.publish(any())
            }
        }
    }
})
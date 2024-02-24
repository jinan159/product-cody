package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.ProductUpdateUseCase.ProductUpdateFailedException
import com.musinsa.product.search.application.admin.port.`in`.ProductUpdateUseCase.UpdateRequest
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
import java.math.BigDecimal
import java.util.Currency

class ProductUpdateServiceTest(
    private val productRepository: com.musinsa.product.search.application.admin.port.out.ProductRepository
) : ServiceShouldSpec({
    val brandRepository = mockk<com.musinsa.product.search.application.admin.port.out.BrandRepository>()
    val categoryRepository = mockk<com.musinsa.product.search.application.admin.port.out.CategoryRepository>()
    val service = com.musinsa.product.search.application.admin.service.ProductUpdateService(
        brandRepository = brandRepository,
        categoryRepository = categoryRepository,
        productRepository = productRepository
    )

    val product = Product(
        brandId = 0L,
        categoryId = 1L,
        name = "test-product-name",
        price = ProductPrice(
            currency = Currency.getInstance("KRW"),
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
            currency = Currency.getInstance("USD"),
            amount = amount
        )

    beforeTest {
        clearMocks(
            brandRepository,
            categoryRepository
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
        val productAfterUpdate = productRepository.findById(product.id!!)

        // then
        productAfterUpdate.shouldNotBeNull()
        productAfterUpdate.id shouldBe product.id
        with(updateRequest) {
            productAfterUpdate.brandId shouldBe brandId
            productAfterUpdate.categoryId shouldBe categoryId
            productAfterUpdate.name shouldBe name
            productAfterUpdate.price.currency shouldBe currency
            productAfterUpdate.price.amount.compareTo(amount) shouldBe 0
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
            exception.shouldBeTypeOf<com.musinsa.product.search.application.exception.BrandNotFoundException>()
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
            exception.shouldBeTypeOf<com.musinsa.product.search.application.exception.CategoryNotFoundException>()
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
            exception.shouldBeTypeOf<com.musinsa.product.search.application.exception.ProductNotFoundException>()
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
        }
    }
})
package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.CreateRequest
import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase.ProductCreateFailedException
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.application.admin.port.out.CategoryRepository
import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.application.exception.BrandNotFoundException
import com.musinsa.product.search.application.exception.CategoryNotFoundException
import com.musinsa.product.search.testsupport.ServiceShouldSpec
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import java.util.Currency

class ProductCreateServiceTest(
    private val productRepository: com.musinsa.product.search.application.admin.port.out.ProductRepository,
) : ServiceShouldSpec({
    val brandRepository = mockk<com.musinsa.product.search.application.admin.port.out.BrandRepository>()
    val categoryRepository = mockk<com.musinsa.product.search.application.admin.port.out.CategoryRepository>()
    val service = com.musinsa.product.search.application.admin.service.ProductCreateService(
        brandRepository = brandRepository,
        categoryRepository = categoryRepository,
        productRepository = productRepository
    )

    val createRequest = CreateRequest(
        brandId = 0L,
        categoryId = 1L,
        name = "test-product-name",
        currency = Currency.getInstance("KRW"),
        amount = 1000.toBigDecimal(),
    )

    beforeTest {
        clearMocks(
            brandRepository,
            categoryRepository
        )
    }

    should("상품 생성을 성공한다") {
        // given
        every { brandRepository.existsById(any()) } returns true
        every { categoryRepository.existsById(any()) } returns true

        // when
        val response = service.create(createRequest)
        val product = productRepository.findById(response.id)

        // then
        product.shouldNotBeNull()
        product.id.shouldNotBeNull()
        with(createRequest) {
            product.brandId shouldBe brandId
            product.categoryId shouldBe categoryId
            product.name shouldBe name
            product.price.currency shouldBe currency
            product.price.amount.compareTo(amount) shouldBe 0
        }
    }

    context("상품을 생성할 때") {
        should("브랜드가 존재하지 않으면 예외가 발생한다") {
            // given
            every { brandRepository.existsById(any()) } returns false

            // when
            val exception = shouldThrowAny {
                service.create(createRequest)
            }

            exception.shouldBeTypeOf<com.musinsa.product.search.application.exception.BrandNotFoundException>()
        }

        should("카테고리 존재하지 않으면 예외가 발생한다") {
            // given
            every { brandRepository.existsById(any()) } returns true
            every { categoryRepository.existsById(any()) } returns false

            // when
            val exception = shouldThrowAny {
                service.create(createRequest)
            }

            exception.shouldBeTypeOf<com.musinsa.product.search.application.exception.CategoryNotFoundException>()
        }

        should("상품 도메인 생성을 실패하면 예외가 발생한다") {
            // given
            every { brandRepository.existsById(any()) } returns true
            every { categoryRepository.existsById(any()) } returns true
            val invalidCreateRequest = createRequest.copy(
                amount = (-1000).toBigDecimal()
            )

            // when
            val exception = shouldThrowAny {
                service.create(invalidCreateRequest)
            }

            exception.shouldBeTypeOf<ProductCreateFailedException>()
        }
    }
})
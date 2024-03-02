package com.musinsa.product.cody.application.product.service

import com.musinsa.product.cody.application.product.port.out.LowestPriceSingleBrandCodySelectRepository
import com.musinsa.product.cody.testsupport.ServiceShouldSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@Transactional
@Sql(scripts = ["classpath:sql/test-data.sql"])
class LowestPriceSingleBrandCodySelectServiceTest(
    private val repository: LowestPriceSingleBrandCodySelectRepository
) : ServiceShouldSpec({
    val service = LowestPriceSingleBrandCodySelectService(
        repository = repository
    )

    context("단일 브랜드로 모든 카테고리 상품 구매할 때") {
        should("최저가격으로 판매하는 브랜드와 카테고리의 상품 가격 총액을 조회한다") {
            // when
            val response = service.select()

            // then
            with(response.minPrice) {
                brandName shouldBe "test-brand-3"
                totalPrice shouldBeEqualComparingTo 3000.toBigDecimal()

                categories.size shouldBe 3
                categories[0].categoryName shouldBe "test-category-1"
                categories[0].price shouldBeEqualComparingTo 800.toBigDecimal()
                categories[1].categoryName shouldBe "test-category-2"
                categories[1].price shouldBeEqualComparingTo 1000.toBigDecimal()
                categories[2].categoryName shouldBe "test-category-3"
                categories[2].price shouldBeEqualComparingTo 1200.toBigDecimal()
            }
        }
    }
})
package com.musinsa.product.cody.application.product.service

import com.musinsa.product.cody.application.product.port.out.ProductPriceExtremesSearchRepository
import com.musinsa.product.cody.testsupport.ServiceShouldSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@Transactional
@Sql(scripts = ["classpath:sql/test-data.sql"])
class ProductPriceExtremesSearchServiceTest(
    private val repository: ProductPriceExtremesSearchRepository
) : ServiceShouldSpec({
    val service = ProductPriceExtremesSearchService(
        repository = repository
    )

    should("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격 조회") {
        // given
        val testCategory1 = "test-category-1"
        val testCategory2 = "test-category-2"
        val testCategory3 = "test-category-3"

        // when
        val response1 = service.search(testCategory1)
        val response2 = service.search(testCategory2)
        val response3 = service.search(testCategory3)

        // then
        response1.minPrices.first().brandName shouldBe "test-brand-3"
        response1.minPrices.first().price shouldBeEqualComparingTo 800.toBigDecimal()
        response1.maxPrices.first().brandName shouldBe "test-brand-2"
        response1.maxPrices.first().price shouldBeEqualComparingTo 1900.toBigDecimal()

        response2.minPrices.first().brandName shouldBe "test-brand-3"
        response2.minPrices.first().price shouldBeEqualComparingTo 1000.toBigDecimal()
        response2.maxPrices.first().brandName shouldBe "test-brand-2"
        response2.maxPrices.first().price shouldBeEqualComparingTo 2000.toBigDecimal()

        response3.minPrices.first().brandName shouldBe "test-brand-1"
        response3.minPrices.first().price shouldBeEqualComparingTo 1100.toBigDecimal()
        response3.maxPrices.first().brandName shouldBe "test-brand-2"
        response3.maxPrices.first().price shouldBeEqualComparingTo 2100.toBigDecimal()
    }
})
package com.musinsa.product.cody.application.product.service

import com.musinsa.product.cody.application.product.port.out.LowestPriceCodySelectRepository
import com.musinsa.product.cody.testsupport.ServiceShouldSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@Transactional
@Sql(scripts = ["classpath:sql/test-data.sql"])
class LowestPriceCodySelectServiceTest(
    private val repository: LowestPriceCodySelectRepository
) : ServiceShouldSpec({
    val service = LowestPriceCodySelectService(
        repository = repository
    )

    should("카테고리 별 최저가격 브랜드와 상품 가격, 총액 조회") {
        // given
        val productCount = 3
        val expectedTotalPrice = 2900.toBigDecimal()

        // when
        val response = service.select()

        // then
        response.totalPrice shouldBeEqualComparingTo expectedTotalPrice
        response.products should {
            it.size shouldBe productCount

            it[0].brandName shouldBe "test-brand-3"
            it[0].categoryName shouldBe "test-category-1"
            it[0].price shouldBeEqualComparingTo 800.toBigDecimal()

            it[1].brandName shouldBe "test-brand-3"
            it[1].categoryName shouldBe "test-category-2"
            it[1].price shouldBeEqualComparingTo 1000.toBigDecimal()

            it[2].brandName shouldBe "test-brand-1"
            it[2].categoryName shouldBe "test-category-3"
            it[2].price shouldBeEqualComparingTo 1100.toBigDecimal()
        }
    }
})
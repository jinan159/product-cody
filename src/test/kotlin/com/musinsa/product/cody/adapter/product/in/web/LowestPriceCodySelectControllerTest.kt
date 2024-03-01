package com.musinsa.product.cody.adapter.product.`in`.web

import com.musinsa.product.cody.adapter.product.out.cache.LowestPriceCodySelectCache
import com.musinsa.product.cody.application.product.port.`in`.LowestPriceCodySelectUseCase
import com.musinsa.product.cody.application.product.port.`in`.LowestPriceCodySelectUseCase.Response
import com.musinsa.product.cody.testsupport.ControllerShouldSpec
import com.musinsa.product.cody.testsupport.ControllerShouldSpec.FieldBuilder.Companion.responseFields
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LowestPriceCodySelectControllerTest : ControllerShouldSpec(
    service = "상품 검색",
    tag = "상품",
    body = {
        val response = Response(
            products = listOf(
                Response.Product(
                    categoryName = "test-category-1-name",
                    brandName = "test-brand-1-name",
                    price = 1000.toBigDecimal()
                ),
                Response.Product(
                    categoryName = "test-category-2-name",
                    brandName = "test-brand-2-name",
                    price = 2000.toBigDecimal()
                )
            ),
            totalPrice = 3000.toBigDecimal()
        )

        should("카테고리별 최저가격 브랜드과 상품가격, 총액 조회에 성공한다") {
            val useCase = mockk<LowestPriceCodySelectUseCase> {
                every { select() } returns response
            }
            val cache = mockk<LowestPriceCodySelectCache> {
                every { get() } returns null
                every { set(any()) } returns Unit
            }

            LowestPriceCodySelectController(
                useCase = useCase,
                cache = cache
            )
                .`when`(
                    get("/v1/product/codies/lowest-price")
                )
                .then(status().isOk)
                .document(
                    responseFields {
                        "products".array("상품 목록") {
                            "categoryName".string("카테고리")
                            "brandName".string("브랜드")
                            "price".number("가격")
                        }
                        "totalPrice".number("총액")
                    }
                )

            verify(exactly = 1) {
                cache.get()
            }
            verify(exactly = 1) {
                useCase.select()
            }
        }

        should("캐시가 있는 경우에는 캐시에서 응답한다") {
            val useCase = mockk<LowestPriceCodySelectUseCase> {
                every { select() } returns response
            }
            val cache = mockk<LowestPriceCodySelectCache> {
                every { get() } returns response
            }

            LowestPriceCodySelectController(
                useCase = useCase,
                cache = cache
            )
                .`when`(
                    get("/v1/product/codies/lowest-price")
                )
                .then(status().isOk)

            verify(exactly = 1) {
                cache.get()
            }
            verify(exactly = 0) {
                useCase.select()
            }
        }
    }
)
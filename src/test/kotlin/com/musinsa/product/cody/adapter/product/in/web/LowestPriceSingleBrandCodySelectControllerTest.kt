package com.musinsa.product.cody.adapter.product.`in`.web

import com.musinsa.product.cody.adapter.product.out.cache.LowestPriceSingleBrandCodySelectCache
import com.musinsa.product.cody.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase
import com.musinsa.product.cody.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase.MinPrice
import com.musinsa.product.cody.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase.Response
import com.musinsa.product.cody.testsupport.ControllerShouldSpec
import com.musinsa.product.cody.testsupport.ControllerShouldSpec.FieldBuilder.Companion.responseFields
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LowestPriceSingleBrandCodySelectControllerTest : ControllerShouldSpec(
    service = "상품 검색",
    tag = "상품",
    body = {
        val response = Response(
            minPrice = MinPrice(
                brandName = "test-brand-name",
                categories = listOf(
                    MinPrice.CategoryAndPrice(
                        categoryName = "test-category-1-name",
                        price = 1000.toBigDecimal()
                    ),
                    MinPrice.CategoryAndPrice(
                        categoryName = "test-category-2-name",
                        price = 2000.toBigDecimal()
                    )
                ),
                totalPrice = 3000.toBigDecimal()
            )
        )

        should("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액 조회에 성공한다") {
            val useCase = mockk<LowestPriceSingleBrandCodySelectUseCase> {
                every { select() } returns response
            }
            val cache = mockk<LowestPriceSingleBrandCodySelectCache> {
                every { get() } returns null
                every { set(any()) } returns Unit
            }

            LowestPriceSingleBrandCodySelectController(
                useCase = useCase,
                cache = cache
            )
                .`when`(
                    get("/v1/product/codies/lowest-price/single-brand")
                )
                .then(status().isOk)
                .document(
                    responseFields {
                        "minPrice".`object`("최저가") {
                            "brandName".string("브랜드")
                            "categories".array("카테고리 목록") {
                                "categoryName".string("카테고리")
                                "price".number("가격")
                            }
                            "totalPrice".number("총액")
                        }
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
            val useCase = mockk<LowestPriceSingleBrandCodySelectUseCase> {
                every { select() } returns response
            }
            val cache = mockk<LowestPriceSingleBrandCodySelectCache> {
                every { get() } returns response
            }

            LowestPriceSingleBrandCodySelectController(
                useCase = useCase,
                cache = cache
            )
                .`when`(
                    get("/v1/product/codies/lowest-price/single-brand")
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
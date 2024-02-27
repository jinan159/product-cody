package com.musinsa.product.search.adapter.product.`in`.web

import com.musinsa.product.search.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase
import com.musinsa.product.search.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase.MinPrice
import com.musinsa.product.search.testsupport.ControllerShouldSpec
import com.musinsa.product.search.testsupport.ControllerShouldSpec.FieldBuilder.Companion.responseFields
import io.mockk.every
import io.mockk.mockk
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LowestPriceSingleBrandCodySelectControllerTest : ControllerShouldSpec(
    service = "상품 검색",
    tag = "상품",
    body = {
        should("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액 조회에 성공한다") {
            val useCase = mockk<LowestPriceSingleBrandCodySelectUseCase> {
                every { select() } returns LowestPriceSingleBrandCodySelectUseCase.Response(
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
            }

            LowestPriceSingleBrandCodySelectController(useCase)
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
        }
    }
)
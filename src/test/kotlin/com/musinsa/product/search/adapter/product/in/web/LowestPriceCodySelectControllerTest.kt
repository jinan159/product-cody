package com.musinsa.product.search.adapter.product.`in`.web

import com.musinsa.product.search.application.product.port.`in`.LowestPriceCodySelectUseCase
import com.musinsa.product.search.application.product.port.`in`.LowestPriceCodySelectUseCase.Response
import com.musinsa.product.search.testsupport.ControllerShouldSpec
import com.musinsa.product.search.testsupport.ControllerShouldSpec.FieldBuilder.Companion.responseFields
import io.mockk.every
import io.mockk.mockk
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LowestPriceCodySelectControllerTest : ControllerShouldSpec(
    service = "상품 검색",
    tag = "상품",
    body = {
        should("카테고리별 최저가격 브랜드과 상품가격, 총액 조회에 성공한다") {
            val useCase = mockk<LowestPriceCodySelectUseCase> {
                every { select() } returns Response(
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
            }

            LowestPriceCodySelectController(useCase)
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
        }
    }
)
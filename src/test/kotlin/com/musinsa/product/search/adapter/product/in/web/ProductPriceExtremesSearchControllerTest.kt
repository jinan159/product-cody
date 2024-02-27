package com.musinsa.product.search.adapter.product.`in`.web

import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase.BrandAndPrice
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase.Response
import com.musinsa.product.search.testsupport.ControllerShouldSpec
import com.musinsa.product.search.testsupport.ControllerShouldSpec.FieldBuilder.Companion.responseFields
import io.mockk.every
import io.mockk.mockk
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProductPriceExtremesSearchControllerTest : ControllerShouldSpec(
    service = "상품 검색",
    tag = "상품",
    body = {
        should("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격 조회에 성공한다") {
            val useCase = mockk<ProductPriceExtremesSearchUseCase> {
                every { search(any()) } returns Response(
                    categoryName = "test-category-name",
                    minPrices = listOf(
                        BrandAndPrice(
                            brandName = "test-brand-1-name",
                            price = 1000.toBigDecimal()
                        )
                    ),
                    maxPrices = listOf(
                        BrandAndPrice(
                            brandName = "test-brand-2-name",
                            price = 2000.toBigDecimal()
                        )
                    )
                )
            }

            ProductPriceExtremesSearchController(useCase)
                .`when`(
                    get(
                        "/v1/product/products/categories/{categoryName}/price-extremes",
                        "test-category-name"
                    )
                )
                .then(status().isOk)
                .document(
                    responseFields {
                        "categoryName".string("카테고리")
                        "minPrices".array("최저가") {
                            "brandName".string("브랜드")
                            "price".number("가격")
                        }
                        "maxPrices".array("최고가") {
                            "brandName".string("브랜드")
                            "price".number("가격")
                        }
                    }
                )
        }
    }
)
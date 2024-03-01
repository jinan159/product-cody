package com.musinsa.product.cody.adapter.product.`in`.web

import com.musinsa.product.cody.adapter.product.out.cache.ProductPriceExtremesSearchCache
import com.musinsa.product.cody.application.product.port.`in`.ProductPriceExtremesSearchUseCase
import com.musinsa.product.cody.application.product.port.`in`.ProductPriceExtremesSearchUseCase.BrandAndPrice
import com.musinsa.product.cody.application.product.port.`in`.ProductPriceExtremesSearchUseCase.Response
import com.musinsa.product.cody.testsupport.ControllerShouldSpec
import com.musinsa.product.cody.testsupport.ControllerShouldSpec.FieldBuilder.Companion.responseFields
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProductPriceExtremesSearchControllerTest : ControllerShouldSpec(
    service = "상품 검색",
    tag = "상품",
    body = {
        val categoryName = "test-category-name"
        val response = Response(
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

        should("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격 조회에 성공한다") {
            val useCase = mockk<ProductPriceExtremesSearchUseCase> {
                every { search(any()) } returns response
            }
            val cache = mockk<ProductPriceExtremesSearchCache> {
                every { getWith(any()) } returns null
                every { setWith(any(), any()) } returns Unit
            }

            ProductPriceExtremesSearchController(
                useCase = useCase,
                cache = cache
            )
                .`when`(
                    get(
                        "/v1/product/products/categories/{categoryName}/price-extremes",
                        categoryName
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

            verify(exactly = 1) {
                cache.getWith(categoryName)
            }
            verify(exactly = 1) {
                useCase.search(categoryName)
            }
        }

        should("캐시가 있는 경우에는 캐시에서 응답한다") {
            val useCase = mockk<ProductPriceExtremesSearchUseCase> {
                every { search(any()) } returns response
            }
            val cache = mockk<ProductPriceExtremesSearchCache> {
                every { getWith(any()) } returns response
            }

            ProductPriceExtremesSearchController(
                useCase = useCase,
                cache = cache
            )
                .`when`(
                    get(
                        "/v1/product/products/categories/{categoryName}/price-extremes",
                        categoryName
                    )
                )
                .then(status().isOk)

            verify(exactly = 1) {
                cache.getWith(categoryName)
            }
            verify(exactly = 0) {
                useCase.search(categoryName)
            }
        }
    }
)
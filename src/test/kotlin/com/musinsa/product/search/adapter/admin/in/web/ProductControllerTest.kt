package com.musinsa.product.search.adapter.admin.`in`.web

import com.musinsa.product.search.application.admin.port.`in`.ProductCreateUseCase
import com.musinsa.product.search.application.admin.port.`in`.ProductDeleteUseCase
import com.musinsa.product.search.application.admin.port.`in`.ProductUpdateUseCase
import com.musinsa.product.search.testsupport.ControllerShouldSpec
import com.musinsa.product.search.testsupport.ControllerShouldSpec.FieldBuilder.Companion.requestFields
import io.mockk.every
import io.mockk.mockk
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.Currency

class ProductControllerTest : ControllerShouldSpec(
    service = "상품 검색 어드민",
    tag = "상품",
    body = {
        val productCreateUseCase = mockk<ProductCreateUseCase>()
        val productUpdateUseCase = mockk<ProductUpdateUseCase>()
        val productDeleteUseCase = mockk<ProductDeleteUseCase>()
        val productController = ProductController(
            productCreateUseCase = productCreateUseCase,
            productUpdateUseCase = productUpdateUseCase,
            productDeleteUseCase = productDeleteUseCase
        )

        should("상품 생성에 성공한다") {
            every { productCreateUseCase.create(any()) } returns ProductCreateUseCase.Response(1L)

            productController
                .`when`(
                    post("/v1/admin/products")
                        .body(
                            ProductCreateUseCase.CreateRequest(
                                brandId = 1L,
                                categoryId = 2L,
                                name = "test-product-name",
                                currency = Currency.getInstance("KRW"),
                                amount = 1000.toBigDecimal()
                            )
                        )
                )
                .then(status().isOk)
                .document(
                    requestFields {
                        "brandId".number("브랜드 ID")
                        "categoryId".number("카테고리 ID")
                        "name".string("상품명")
                        "currency".string("통화 코드")
                        "amount".number("가격")
                    }
                )
        }

        should("상품 수정에 성공한다") {
            every { productUpdateUseCase.update(any()) } returns Unit

            productController
                .`when`(
                    put(
                        "/v1/admin/products/{id}",
                        0L
                    )
                        .body(
                            ProductController.UpdateRequestBody(
                                brandId = 1L,
                                categoryId = 2L,
                                name = "test-product-updated-name",
                                currency = Currency.getInstance("KRW"),
                                amount = 2000.toBigDecimal()
                            )
                        )
                )
                .then(status().isOk)
                .document(
                    pathParameters(
                        "id" isParameterFor "상품 ID"
                    ),
                    requestFields {
                        "brandId".number("브랜드 ID")
                        "categoryId".number("카테고리 ID")
                        "name".string("상품명")
                        "currency".string("통화 코드")
                        "amount".number("가격")
                    }
                )
        }

        should("상품 삭제에 성공한다") {
            every { productDeleteUseCase.delete(any()) } returns Unit

            productController
                .`when`(
                    delete(
                        "/v1/admin/products/{id}",
                           0L
                    )
                )
                .then(status().isOk)
                .document(
                    pathParameters(
                        "id" isParameterFor "상품 ID"
                    )
                )
        }
    }
)
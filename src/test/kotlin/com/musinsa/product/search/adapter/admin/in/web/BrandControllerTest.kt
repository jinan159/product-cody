package com.musinsa.product.search.adapter.admin.`in`.web

import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase
import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase.CreateRequest
import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase.Response
import com.musinsa.product.search.testsupport.ControllerShouldSpec
import com.musinsa.product.search.testsupport.ControllerShouldSpec.FieldBuilder.Companion.requestFields
import io.mockk.every
import io.mockk.mockk
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class BrandControllerTest : ControllerShouldSpec(
    service = "상품 검색 어드민",
    tag = "브랜드",
    body = {
        should("브랜드 생성에 성공한다") {
            val brandCreateUseCase = mockk<BrandCreateUseCase> {
                every { create(any()) } returns Response(1L)
            }

            BrandController(brandCreateUseCase)
                .`when`(
                    post("/v1/admin/brands")
                        .body(
                            CreateRequest(
                                name = "test-brand-name"
                            )
                        )
                )
                .then(status().isOk)
                .document(
                    requestFields {
                        "name".string("브랜드명")
                    }
                )
        }
    }
)
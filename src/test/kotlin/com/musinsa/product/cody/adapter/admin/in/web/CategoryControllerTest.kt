package com.musinsa.product.cody.adapter.admin.`in`.web

import com.musinsa.product.cody.application.admin.port.`in`.CategoryCreateUseCase
import com.musinsa.product.cody.application.admin.port.`in`.CategoryCreateUseCase.CreateRequest
import com.musinsa.product.cody.application.admin.port.`in`.CategoryCreateUseCase.Response
import com.musinsa.product.cody.testsupport.ControllerShouldSpec
import com.musinsa.product.cody.testsupport.ControllerShouldSpec.FieldBuilder.Companion.requestFields
import io.mockk.every
import io.mockk.mockk
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CategoryControllerTest : ControllerShouldSpec(
    service = "상품 검색 어드민",
    tag = "카테고리",
    body = {
        should("카테고리 생성에 성공한다") {
            val categoryCreateUseCase = mockk<CategoryCreateUseCase> {
                every { create(any()) } returns Response(1L)
            }

            CategoryController(categoryCreateUseCase)
                .`when`(
                    post("/v1/admin/categories")
                        .body(
                            CreateRequest(
                                name = "test-category-name"
                            )
                        )
                )
                .then(status().isOk)
                .document(
                    requestFields {
                        "name".string("카테고리명")
                    }
                )
        }
    }
)
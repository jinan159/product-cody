package com.musinsa.product.cody.application.admin.service

import com.musinsa.product.cody.application.admin.port.`in`.CategoryCreateUseCase.CreateRequest
import com.musinsa.product.cody.application.admin.port.out.CategoryRepository
import com.musinsa.product.cody.testsupport.ServiceShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.transaction.annotation.Transactional

@Transactional
class CategoryCreateServiceTest(
    private val categoryRepository: CategoryRepository
) : ServiceShouldSpec({
    val service = CategoryCreateService(
        categoryRepository = categoryRepository
    )

    should("카테고리 생성에 성공한다") {
        // given
        val name = "test-category-name"

        // when
        val response = service.create(CreateRequest(name))
        val category = categoryRepository.findById(response.id)

        // then
        category.shouldNotBeNull()
        category.name shouldBe name
    }
})
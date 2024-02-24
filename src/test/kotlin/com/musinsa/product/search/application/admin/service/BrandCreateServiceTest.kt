package com.musinsa.product.search.application.admin.service

import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase.CreateRequest
import com.musinsa.product.search.application.admin.port.out.BrandRepository
import com.musinsa.product.search.testsupport.ServiceShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class BrandCreateServiceTest(
    private val brandRepository: com.musinsa.product.search.application.admin.port.out.BrandRepository
) : ServiceShouldSpec({
    val service = com.musinsa.product.search.application.admin.service.BrandCreateService(
        brandRepository = brandRepository
    )

    should("브랜드 생성에 성공한다") {
        // given
        val name = "test-brand-name"

        // when
        val response = service.create(CreateRequest(name))
        val brand = brandRepository.findById(response.id)

        // then
        brand.shouldNotBeNull()
        brand.name shouldBe name
    }
})
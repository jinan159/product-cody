package com.musinsa.product.cody.application.admin.service

import com.musinsa.product.cody.application.admin.port.`in`.BrandCreateUseCase.CreateRequest
import com.musinsa.product.cody.application.admin.port.out.BrandRepository
import com.musinsa.product.cody.testsupport.ServiceShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.transaction.annotation.Transactional

@Transactional
class BrandCreateServiceTest(
    private val brandRepository: BrandRepository
) : ServiceShouldSpec({
    val service = BrandCreateService(
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
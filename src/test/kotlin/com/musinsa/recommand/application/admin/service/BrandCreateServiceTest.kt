package com.musinsa.recommand.application.admin.service

import com.musinsa.recommand.application.admin.port.out.BrandRepository
import io.kotest.core.spec.style.StringSpec
import io.mockk.mockk

class BrandCreateServiceTest : StringSpec({
    val brandRepository = mockk<BrandRepository>()
    val service = BrandCreateService(
        brandRepository = brandRepository
    )

    "브랜드 생성에 성공한다" {

    }
})
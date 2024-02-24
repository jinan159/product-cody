package com.musinsa.recommand.application.admin.service

import com.musinsa.recommand.application.admin.port.out.ProductRepository
import io.kotest.core.spec.style.StringSpec
import io.mockk.mockk

class ProductCreateServiceTest : StringSpec({
    val productValidator = mockk<ProductValidator>()
    val productRepository = mockk<ProductRepository>()
    val service = ProductCreateService(
        productValidator = productValidator,
        productRepository = productRepository
    )

    "상품 생성을 성공한다" {

    }

    "상품을 생성할 때 검증에 실패하면 상품이 생성되지 않는다" {

    }
})
package com.musinsa.recommand.application.admin.service

import com.musinsa.recommand.application.admin.port.out.ProductRepository
import io.kotest.core.spec.style.StringSpec
import io.mockk.mockk

class ProductUpdateServiceTest : StringSpec({
    val productValidator = mockk<ProductValidator>()
    val productRepository = mockk<ProductRepository>()
    val service = ProductUpdateService(
        productValidator = productValidator,
        productRepository = productRepository
    )

    "상품 수정을 성공한다" {

    }

    "상품을 수정할 때 검증에 실패하면 상품이 생성되지 않는다" {

    }
})
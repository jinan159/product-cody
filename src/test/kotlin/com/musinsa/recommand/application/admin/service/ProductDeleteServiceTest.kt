package com.musinsa.recommand.application.admin.service

import com.musinsa.recommand.application.admin.port.out.ProductRepository
import io.kotest.core.spec.style.StringSpec
import io.mockk.mockk

class ProductDeleteServiceTest : StringSpec({
    val productRepository = mockk<ProductRepository>()
    val service = ProductDeleteService(
        productRepository = productRepository
    )

    "상품 삭제를 성공한다" {

    }

    "상품을 삭제할 때 존재하지 않는 상품이면 예외가 발생한다" {

    }
})
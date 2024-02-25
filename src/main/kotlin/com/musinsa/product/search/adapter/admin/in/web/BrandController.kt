package com.musinsa.product.search.adapter.admin.`in`.web

import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase
import com.musinsa.product.search.application.admin.port.`in`.BrandCreateUseCase.CreateRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admin/brands")
class BrandController(
    private val brandCreateUseCase: BrandCreateUseCase
) {
    @PostMapping
    fun create(
        @RequestBody requestBody: CreateRequest
    ) {
        brandCreateUseCase.create(requestBody)
    }
}
package com.musinsa.product.cody.adapter.admin.`in`.web

import com.musinsa.product.cody.application.admin.port.`in`.CategoryCreateUseCase
import com.musinsa.product.cody.application.admin.port.`in`.CategoryCreateUseCase.CreateRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admin/categories")
class CategoryController(
    private val categoryCreateUseCase: CategoryCreateUseCase
) {
    @PostMapping
    fun create(
        @RequestBody requestBody: CreateRequest
    ) {
        categoryCreateUseCase.create(requestBody)
    }
}
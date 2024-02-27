package com.musinsa.product.search.adapter.product.`in`.web

import com.musinsa.product.search.application.product.port.`in`.LowestPriceCodySelectUseCase
import com.musinsa.product.search.application.product.port.`in`.LowestPriceCodySelectUseCase.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/product/codies/lowest-price")
class LowestPriceCodySelectController(
    private val lowestPriceCodySelectUseCase: LowestPriceCodySelectUseCase
) {
    @GetMapping
    fun select(): Response {
        return lowestPriceCodySelectUseCase.select()
    }
}
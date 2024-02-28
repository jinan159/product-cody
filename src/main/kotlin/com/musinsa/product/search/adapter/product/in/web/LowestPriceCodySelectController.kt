package com.musinsa.product.search.adapter.product.`in`.web

import com.musinsa.product.search.adapter.product.out.cache.LowestPriceCodySelectCache
import com.musinsa.product.search.application.product.port.`in`.LowestPriceCodySelectUseCase
import com.musinsa.product.search.application.product.port.`in`.LowestPriceCodySelectUseCase.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/product/codies/lowest-price")
class LowestPriceCodySelectController(
    private val useCase: LowestPriceCodySelectUseCase,
    private val cache: LowestPriceCodySelectCache
) {
    @GetMapping
    fun select(): Response {
        val response = cache.get()
            ?: useCase.select()
                .also { cache.set(it) }

        return response
    }
}
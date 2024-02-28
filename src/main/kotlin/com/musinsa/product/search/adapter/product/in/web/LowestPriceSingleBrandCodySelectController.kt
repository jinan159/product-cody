package com.musinsa.product.search.adapter.product.`in`.web

import com.musinsa.product.search.adapter.product.out.cache.LowestPriceSingleBrandCodySelectCache
import com.musinsa.product.search.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase
import com.musinsa.product.search.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/product/codies/lowest-price/single-brand")
class LowestPriceSingleBrandCodySelectController(
    private val useCase: LowestPriceSingleBrandCodySelectUseCase,
    private val cache: LowestPriceSingleBrandCodySelectCache
) {
    @GetMapping
    fun select(): Response {
        val response = cache.get()
            ?: useCase.select()
                .also { cache.set(it) }

        return response
    }
}
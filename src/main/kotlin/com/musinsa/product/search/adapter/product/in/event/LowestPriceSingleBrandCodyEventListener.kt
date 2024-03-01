package com.musinsa.product.search.adapter.product.`in`.event

import com.musinsa.product.search.adapter.common.event.ProductChangedEvent
import com.musinsa.product.search.adapter.product.out.cache.LowestPriceSingleBrandCodySelectCache
import com.musinsa.product.search.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase
import com.musinsa.product.search.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase.Response
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping

@Component
class LowestPriceSingleBrandCodyEventListener(
    private val useCase: LowestPriceSingleBrandCodySelectUseCase,
    private val cache: LowestPriceSingleBrandCodySelectCache
) {
    @EventListener
    fun handleEvent(event: ProductChangedEvent) {
        cache.set(
            useCase.select()
        )
    }
}
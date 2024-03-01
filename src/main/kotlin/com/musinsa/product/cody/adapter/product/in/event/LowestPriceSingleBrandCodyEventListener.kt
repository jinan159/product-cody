package com.musinsa.product.cody.adapter.product.`in`.event

import com.musinsa.product.cody.adapter.common.event.ProductChangedEvent
import com.musinsa.product.cody.adapter.product.out.cache.LowestPriceSingleBrandCodySelectCache
import com.musinsa.product.cody.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

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
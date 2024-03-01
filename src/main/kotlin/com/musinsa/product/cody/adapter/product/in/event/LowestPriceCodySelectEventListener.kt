package com.musinsa.product.cody.adapter.product.`in`.event

import com.musinsa.product.cody.adapter.common.event.ProductChangedEvent
import com.musinsa.product.cody.adapter.product.out.cache.LowestPriceCodySelectCache
import com.musinsa.product.cody.application.product.port.`in`.LowestPriceCodySelectUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class LowestPriceCodySelectEventListener(
    private val useCase: LowestPriceCodySelectUseCase,
    private val cache: LowestPriceCodySelectCache
) {
    @EventListener
    fun handleEvent(event: ProductChangedEvent) {
        cache.set(
            useCase.select()
        )
    }
}
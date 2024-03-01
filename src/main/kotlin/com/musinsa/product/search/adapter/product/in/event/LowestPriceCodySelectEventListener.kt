package com.musinsa.product.search.adapter.product.`in`.event

import com.musinsa.product.search.adapter.common.event.ProductChangedEvent
import com.musinsa.product.search.adapter.product.out.cache.LowestPriceCodySelectCache
import com.musinsa.product.search.application.product.port.`in`.LowestPriceCodySelectUseCase
import com.musinsa.product.search.application.product.port.`in`.LowestPriceCodySelectUseCase.Response
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping

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
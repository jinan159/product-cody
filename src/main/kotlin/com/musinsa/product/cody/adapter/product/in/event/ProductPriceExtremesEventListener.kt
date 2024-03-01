package com.musinsa.product.cody.adapter.product.`in`.event

import com.musinsa.product.cody.adapter.common.event.ProductChangedEvent
import com.musinsa.product.cody.adapter.product.out.cache.ProductPriceExtremesSearchCache
import com.musinsa.product.cody.application.product.port.`in`.ProductGetUseCase
import com.musinsa.product.cody.application.product.port.`in`.ProductPriceExtremesSearchUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ProductPriceExtremesEventListener(
    private val useCase: ProductPriceExtremesSearchUseCase,
    private val productGetUseCase: ProductGetUseCase,
    private val cache: ProductPriceExtremesSearchCache
) {
    @EventListener
    fun handleEvent(event: ProductChangedEvent) {
        val productResponse = productGetUseCase.get(event.productId)

        cache.setWith(
            categoryName = productResponse.categoryName,
            response = useCase.search(productResponse.categoryName)
        )
    }
}
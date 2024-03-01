package com.musinsa.product.search.adapter.product.`in`.event

import com.musinsa.product.search.adapter.common.event.ProductChangedEvent
import com.musinsa.product.search.adapter.product.out.cache.ProductPriceExtremesSearchCache
import com.musinsa.product.search.application.admin.port.out.ProductRepository
import com.musinsa.product.search.application.product.port.`in`.ProductGetUseCase
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase.Response
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

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
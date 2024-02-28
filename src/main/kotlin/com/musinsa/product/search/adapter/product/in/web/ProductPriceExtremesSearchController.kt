package com.musinsa.product.search.adapter.product.`in`.web

import com.musinsa.product.search.adapter.product.out.cache.ProductPriceExtremesSearchCache
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/product/products/categories/{categoryName}/price-extremes")
class ProductPriceExtremesSearchController(
    private val useCase: ProductPriceExtremesSearchUseCase,
    private val cache: ProductPriceExtremesSearchCache
) {
    @GetMapping
    fun search(
        @PathVariable categoryName: String
    ): Response {
        val response = cache.getWith(categoryName)
            ?: useCase.search(categoryName)
                .also { cache.setWith(
                    categoryName = categoryName,
                    response = it
                ) }

        return response
    }
}
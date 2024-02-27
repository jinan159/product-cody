package com.musinsa.product.search.adapter.product.`in`.web

import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/product/products/categories/{categoryName}/price-extremes")
class ProductPriceExtremesSearchController(
    private val useCase: ProductPriceExtremesSearchUseCase
) {
    @GetMapping
    fun search(
        @PathVariable categoryName: String
    ): Response {
        return useCase.search(categoryName)
    }
}
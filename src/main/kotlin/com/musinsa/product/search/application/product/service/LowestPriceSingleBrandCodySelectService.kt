package com.musinsa.product.search.application.product.service

import com.musinsa.product.search.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase
import com.musinsa.product.search.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase.LowestPriceSingleBrandCodyNotFoundException
import com.musinsa.product.search.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase.MinPrice
import com.musinsa.product.search.application.product.port.`in`.LowestPriceSingleBrandCodySelectUseCase.Response
import com.musinsa.product.search.application.product.port.out.LowestPriceSingleBrandCodySelectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LowestPriceSingleBrandCodySelectService(
    private val repository: LowestPriceSingleBrandCodySelectRepository
) : LowestPriceSingleBrandCodySelectUseCase {
    override fun select(): Response {
        val selectResult = repository.select()
            ?: throw LowestPriceSingleBrandCodyNotFoundException()

        return Response(
            minPrice = MinPrice(
                brandName = selectResult.brandName,
                categories = selectResult.categories
                    .map {
                        MinPrice.CategoryAndPrice(
                            categoryName = it.categoryName,
                            price = it.price,
                        )
                    },
                totalPrice = selectResult.categories.sumOf { it.price }
            )
        )
    }
}
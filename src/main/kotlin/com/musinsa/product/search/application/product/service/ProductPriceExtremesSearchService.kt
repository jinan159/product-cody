package com.musinsa.product.search.application.product.service

import com.musinsa.product.search.application.exception.CategoryNotFoundException
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase.BrandAndPrice
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase.ProductPriceExtremesNotFoundException
import com.musinsa.product.search.application.product.port.`in`.ProductPriceExtremesSearchUseCase.Response
import com.musinsa.product.search.application.product.port.out.ProductPriceExtremesSearchRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductPriceExtremesSearchService(
    private val repository: ProductPriceExtremesSearchRepository
) : ProductPriceExtremesSearchUseCase {
    override fun search(categoryName: String): Response {
        val categorySearchResult = repository.searchCategoryIdByCategoryName(categoryName)
            ?: throw CategoryNotFoundException()

        val searchResult = repository.search(categorySearchResult.id)
            ?: throw ProductPriceExtremesNotFoundException()

        return Response(
            categoryName = categorySearchResult.name,
            minPrices = listOf(
                BrandAndPrice(
                    brandName = searchResult.minPrice.brandName,
                    price = searchResult.minPrice.price,
                )
            ),
            maxPrices = listOf(
                BrandAndPrice(
                    brandName = searchResult.maxPrice.brandName,
                    price = searchResult.maxPrice.price,
                )
            ),
        )
    }
}
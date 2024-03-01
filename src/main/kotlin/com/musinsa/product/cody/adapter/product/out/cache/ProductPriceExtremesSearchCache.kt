package com.musinsa.product.cody.adapter.product.out.cache

import com.musinsa.product.cody.application.product.port.`in`.ProductPriceExtremesSearchUseCase.Response
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Component
class ProductPriceExtremesSearchCache(
    private val client: RedissonClient
) {
    private fun key(categoryName: String)
        = "product:cody:v1:product:products:categories:$categoryName:price-extremes"

    fun getWith(categoryName: String): Response? {
        val bucket = client.getBucket<Response>(
            key(categoryName)
        )
        return bucket.get() ?: null
    }

    fun setWith(
        categoryName: String,
        response: Response
    ) {
        val bucket = client.getBucket<Response>(
            key(categoryName)
        )
        bucket.set(response, 5.minutes)
    }
}
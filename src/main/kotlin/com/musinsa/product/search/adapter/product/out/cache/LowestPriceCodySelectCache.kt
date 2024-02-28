package com.musinsa.product.search.adapter.product.out.cache

import com.musinsa.product.search.application.product.port.`in`.LowestPriceCodySelectUseCase.Response
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Component
class LowestPriceCodySelectCache(
    private val client: RedissonClient
) {
    private val key = "product:search:v1:product:codies:lowest-price"

    fun get(): Response? {
        val bucket = client.getBucket<Response>(key)
        return bucket.get() ?: null
    }

    fun set(response: Response) {
        val bucket = client.getBucket<Response>(key)
        bucket.set(response, 5.minutes)
    }
}
package com.musinsa.product.search.application.admin.port.out

interface ProductChangedEventPublisher {
    fun publish(payload: EventPayload)

    data class EventPayload(
        val productId: Long
    )
}
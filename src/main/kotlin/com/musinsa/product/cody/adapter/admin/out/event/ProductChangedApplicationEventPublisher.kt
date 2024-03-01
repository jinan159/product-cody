package com.musinsa.product.cody.adapter.admin.out.event

import com.musinsa.product.cody.adapter.common.event.ProductChangedEvent
import com.musinsa.product.cody.application.admin.port.out.ProductChangedEventPublisher
import com.musinsa.product.cody.application.admin.port.out.ProductChangedEventPublisher.EventPayload
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class ProductChangedApplicationEventPublisher(
    private val publisher: ApplicationEventPublisher
) : ProductChangedEventPublisher {
    override fun publish(payload: EventPayload) {
        publisher.publishEvent(
            ProductChangedEvent(
                productId = payload.productId
            )
        )
    }
}
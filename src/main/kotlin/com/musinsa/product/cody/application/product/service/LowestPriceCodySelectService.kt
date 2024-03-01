package com.musinsa.product.cody.application.product.service

import com.musinsa.product.cody.application.product.port.`in`.LowestPriceCodySelectUseCase
import com.musinsa.product.cody.application.product.port.`in`.LowestPriceCodySelectUseCase.Response
import com.musinsa.product.cody.application.product.port.out.LowestPriceCodySelectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LowestPriceCodySelectService(
    private val repository: LowestPriceCodySelectRepository
) : LowestPriceCodySelectUseCase {
    override fun select(): Response {
        val result = repository.select()

        return Response(
            products = result.items.map {
                Response.Product(
                    categoryName = it.categoryName,
                    brandName = it.brandName,
                    price = it.amount,
                )
            },
            totalPrice = result.items.sumOf { it.amount }
        )
    }
}
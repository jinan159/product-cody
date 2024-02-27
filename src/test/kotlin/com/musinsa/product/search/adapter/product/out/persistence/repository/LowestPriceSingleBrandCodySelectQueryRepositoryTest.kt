package com.musinsa.product.search.adapter.product.out.persistence.repository

import com.musinsa.product.search.application.product.port.out.LowestPriceSingleBrandCodySelectRepository
import com.musinsa.product.search.testsupport.ServiceShouldSpec
import org.springframework.transaction.annotation.Transactional

@Transactional
class LowestPriceSingleBrandCodySelectQueryRepositoryTest(
    private val repository: LowestPriceSingleBrandCodySelectRepository
) : ServiceShouldSpec({
    should("asdfasdf") {
        repository.select()
    }
})
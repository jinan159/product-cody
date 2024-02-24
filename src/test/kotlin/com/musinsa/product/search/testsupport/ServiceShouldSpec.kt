package com.musinsa.product.search.testsupport

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
abstract class ServiceShouldSpec(body: ShouldSpec.() -> Unit = {}) : ShouldSpec(body) {
    override fun extensions(): List<Extension>
        = listOf(SpringExtension)
}
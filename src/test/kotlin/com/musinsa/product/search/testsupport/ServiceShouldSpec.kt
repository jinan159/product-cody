package com.musinsa.product.search.testsupport

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ImportAutoConfiguration(ExposedAutoConfiguration::class)
abstract class ServiceShouldSpec(body: ShouldSpec.() -> Unit = {}) : ShouldSpec(body) {
    override fun extensions(): List<Extension>
        = listOf(SpringExtension)
}
package com.musinsa.product.cody.testsupport

import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

@ActiveProfiles("test")
@Configuration
class RedisTestContainerConfiguration {
    init {
        GenericContainer(DockerImageName.parse("redis:7.2-alpine"))
            .withExposedPorts(6379)
            .withReuse(true)
            .also {
                it.start()

                System.setProperty("spring.redis.host", it.getHost())
                System.setProperty("spring.redis.port", it.getMappedPort(6379).toString())
                System.setProperty("spring.data.redis.host", it.getHost())
                System.setProperty("spring.data.redis.port", it.getMappedPort(6379).toString())
            }
    }
}
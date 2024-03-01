package com.musinsa.product.cody.testsupport

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule

val objectMapper = jacksonMapperBuilder()
    .addModule(JavaTimeModule())
    .addModule(ParameterNamesModule())
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    .build() ?: jacksonObjectMapper()

inline fun <reified T> T.toJson(): String {
    return objectMapper.writeValueAsString(this)
}
package com.musinsa.product.cody.testsupport

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import io.kotest.common.KotestInternal
import io.kotest.core.spec.style.ShouldSpec
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.JsonFieldType.ARRAY
import org.springframework.restdocs.payload.JsonFieldType.BOOLEAN
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.JsonFieldType.OBJECT
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import kotlin.text.Charsets.UTF_8

abstract class ControllerShouldSpec(
    private val service: String,
    private val tag: String,
    private val controllerAdvices: List<Any> = emptyList(),
    body: ControllerShouldSpec.() -> Unit = {}
) : ShouldSpec() {
    private val documentation = ManualRestDocumentation()
    private lateinit var identifier: String
    private lateinit var description: String

    init {
        @OptIn(KotestInternal::class)
        beforeEach {
            identifier = "[$service] $tag ${it.descriptor.parts().joinToString(" ")}"
            description = it.descriptor.parts().let { parts ->
                parts.take(parts.size - 1).joinToString(" ")
            }

            documentation.beforeTest(
                it.javaClass,
                it.name.testName
            )
        }

        afterEach {
            documentation.afterTest()
        }

        body()
    }

    fun <T> T.`when`(builder: RequestBuilder) =
        mockMvc()
            .perform(builder)
            .andDo(print())

    private fun <T> T.mockMvc(): MockMvc {
        val objectMapper: ObjectMapper = Jackson2ObjectMapperBuilder.json()
            .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS)
            .build()

        return MockMvcBuilders.standaloneSetup(this)
            .setControllerAdvice(
                *controllerAdvices.toTypedArray()
            )
            .setMessageConverters(
                MappingJackson2HttpMessageConverter(objectMapper)
            )
            .apply<StandaloneMockMvcBuilder>(
                documentationConfiguration(documentation)
            )
            .build()
    }

    fun ResultActions.then(vararg resultMatchers: ResultMatcher): ResultActions {
        return resultMatchers.fold(this) { resultActions, resultMatcher ->
            resultActions.andExpect(resultMatcher)
        }
    }

    inline fun <reified T> MockHttpServletRequestBuilder.body(body: T) =
        content(body.toJson())
            .contentType(APPLICATION_JSON)
            .characterEncoding(UTF_8)

    infix fun String.isParameterFor(description: String): ParameterDescriptor {
        return parameterWithName(this).description(description)
    }

    private fun basicDocument(
        vararg snippets: Snippet
    ): RestDocumentationResultHandler {
        return MockMvcRestDocumentationWrapper.document(
            identifier = identifier,
            resourceDetails = ResourceSnippetParametersBuilder()
                .tag("[$service] $tag")
                .description(description),
            snippets = snippets
        )
    }

    fun ResultActions.document(vararg snippets: Snippet): ResultActions {
        return andDo(
            basicDocument(
                snippets = snippets
            )
        )
    }

    class FieldBuilder(
        private val pathPrefix: String = "",
        private val fields: MutableList<Field> = mutableListOf()
    ) {
        private val fieldDescriptors: List<FieldDescriptor>
            get() = fields.flatMap { it.fieldDescriptors() }

        fun toResponseFieldsSnippet() = responseFields(fieldDescriptors)!!
        fun toRequestFieldsSnippet() = requestFields(fieldDescriptors)!!

        private fun add(field: Field) {
            fields += field
        }

        fun array(
            build: FieldBuilder.() -> Unit = {}
        ) {
            FieldBuilder("$pathPrefix[].")
                .apply(build)
                .fields
                .forEach {
                    add(it)
                }
        }

        fun String.array(
            description: String,
            optional: Boolean = false,
            ignoreBody: Boolean = false,
            build: FieldBuilder.() -> Unit = {}
        ) {
            "$pathPrefix$this".let { path ->
                when (ignoreBody) {
                    false -> DefaultField(
                        path = path,
                        type = ARRAY,
                        description = description,
                        optional = optional,
                        children = FieldBuilder("$path[].")
                            .apply(build).fields
                    ).let { add(it) }

                    true -> SubsectionField(
                        path = "$pathPrefix$this",
                        type = ARRAY,
                        description = description,
                        optional = optional
                    ).let { add(it) }
                }
            }
        }

        fun String.`object`(
            description: String,
            optional: Boolean = false,
            ignoreBody: Boolean = false,
            build: FieldBuilder.() -> Unit = {}
        ) {
            "$pathPrefix$this".let { path ->
                when (ignoreBody) {
                    false -> DefaultField(
                        path = path,
                        type = OBJECT,
                        description = description,
                        optional = optional,
                        children = FieldBuilder("$path.")
                            .apply { build() }.fields
                    ).let { add(it) }

                    true -> SubsectionField(
                        path = "$pathPrefix$this",
                        type = OBJECT,
                        description = description,
                        optional = optional
                    ).let { add(it) }
                }
            }
        }

        fun String.string(
            description: String,
            optional: Boolean = false
        ) {
            DefaultField(
                path = "$pathPrefix$this",
                type = STRING,
                optional = optional,
                description = description
            ).let { add(it) }
        }

        fun String.number(
            description: String,
            optional: Boolean = false
        ) {
            DefaultField(
                path = "$pathPrefix$this",
                type = NUMBER,
                optional = optional,
                description = description
            ).let { add(it) }
        }

        fun String.boolean(
            description: String,
            optional: Boolean = false
        ) {
            DefaultField(
                path = "$pathPrefix$this",
                type = BOOLEAN,
                optional = optional,
                description = description
            ).let { add(it) }
        }

        interface Field {
            val path: String
            val type: JsonFieldType
            val description: String
            val optional: Boolean
            fun fieldDescriptors(): List<FieldDescriptor>
        }

        data class DefaultField(
            override val path: String,
            override val type: JsonFieldType,
            override val description: String,
            override val optional: Boolean = false,
            val children: List<Field> = emptyList()
        ) : Field {
            override fun fieldDescriptors(): List<FieldDescriptor> {
                return listOf(
                    fieldWithPath(path)
                        .type(type)
                        .description(description)
                        .let {
                            if (optional) it.optional() else it
                        }
                ) + children.flatMap {
                    it.fieldDescriptors()
                }
            }
        }

        data class SubsectionField(
            override val path: String,
            override val type: JsonFieldType,
            override val description: String,
            override val optional: Boolean = false
        ) : Field {
            override fun fieldDescriptors(): List<FieldDescriptor> {
                return listOf(
                    subsectionWithPath(path)
                        .type(type)
                        .description(description)
                        .let {
                            if (optional) it.optional() else it
                        }
                )
            }
        }

        companion object {
            private fun fields(build: FieldBuilder.() -> Unit): FieldBuilder {
                return FieldBuilder().apply { build() }
            }

            fun responseFields(build: FieldBuilder.() -> Unit): ResponseFieldsSnippet {
                return fields(build).toResponseFieldsSnippet()
            }

            fun requestFields(build: FieldBuilder.() -> Unit): RequestFieldsSnippet {
                return fields(build).toRequestFieldsSnippet()
            }
        }
    }
}

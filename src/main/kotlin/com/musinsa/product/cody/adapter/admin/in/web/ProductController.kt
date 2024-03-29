package com.musinsa.product.cody.adapter.admin.`in`.web

import com.musinsa.product.cody.application.admin.port.`in`.ProductCreateUseCase
import com.musinsa.product.cody.application.admin.port.`in`.ProductCreateUseCase.CreateRequest
import com.musinsa.product.cody.application.admin.port.`in`.ProductDeleteUseCase
import com.musinsa.product.cody.application.admin.port.`in`.ProductUpdateUseCase
import com.musinsa.product.cody.application.admin.port.`in`.ProductUpdateUseCase.UpdateRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/v1/admin/products")
class ProductController(
    private val productCreateUseCase: ProductCreateUseCase,
    private val productUpdateUseCase: ProductUpdateUseCase,
    private val productDeleteUseCase: ProductDeleteUseCase
) {
    @PostMapping
    fun create(
        @RequestBody requestBody: CreateRequest
    ) {
        productCreateUseCase.create(requestBody)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody requestBody: UpdateRequestBody
    ) {
        productUpdateUseCase.update(
            request = with(requestBody) {
                UpdateRequest(
                    id = id,
                    brandId = brandId,
                    categoryId = categoryId,
                    name = name,
                    amount = amount,
                )
            }
        )
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ) {
        productDeleteUseCase.delete(id)
    }

    data class UpdateRequestBody(
        val brandId: Long?,
        val categoryId: Long?,
        val name: String?,
        val amount: BigDecimal?
    )
}
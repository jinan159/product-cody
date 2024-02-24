package com.musinsa.product.search.adapter.admin.out.persistence.entity

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.Currency

@Entity
@Table(name = "products")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column
    var categoryId: Long,

    @Column
    var brandId: Long,

    @Column(length = 255)
    var name: String,

    @Column(length = 255)
    @Convert(converter = com.musinsa.product.search.adapter.admin.out.persistence.entity.CurrencyConverter::class)
    var currency: Currency,

    @Column(precision = 10, scale = 2)
    var amount: BigDecimal
)

@Converter(autoApply = true)
class CurrencyConverter : AttributeConverter<Currency, String> {
    override fun convertToDatabaseColumn(currency: Currency?): String {
        require(currency != null) {
            "통화가 올바르지 않습니다"
        }

        return currency.toString()
    }

    override fun convertToEntityAttribute(currency: String?): Currency {
        return Currency.getInstance(currency)
    }
}
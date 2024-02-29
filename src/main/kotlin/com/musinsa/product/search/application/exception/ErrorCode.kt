package com.musinsa.product.search.application.exception

enum class ErrorCode(val message: String) {
    PRODUCT_PRICE_EXTREMES_NOT_FOUND("최저, 최고 가격 상품을 찾을 수 없습니다"),
    LOWEST_PRICE_SINGLE_BRAND_NOT_FOUND("최저 가격인 단일 브랜드 코디를 찾을 수 없습니다"),
    BRAND_NOT_FOUND("존재하지 않는 브랜드 입니다"),
    CATEGORY_NOT_FOUND("존재하지 않는 카테고리 입니다"),
    PRODUCT_NOT_FOUND("존재하지 않는 상품 입니다"),
    PRODUCT_CREATE_FAILED("상품 생성에 실패했습니다"),
    PRODUCT_UPDATE_FAILED("상품 수정에 실패했습니다")
}
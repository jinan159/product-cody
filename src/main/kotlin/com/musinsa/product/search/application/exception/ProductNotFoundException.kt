package com.musinsa.product.search.application.exception

import com.musinsa.product.search.application.exception.ErrorCode.PRODUCT_NOT_FOUND

class ProductNotFoundException : NotFoundException(PRODUCT_NOT_FOUND)
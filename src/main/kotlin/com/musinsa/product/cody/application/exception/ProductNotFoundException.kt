package com.musinsa.product.cody.application.exception

import com.musinsa.product.cody.application.exception.ErrorCode.PRODUCT_NOT_FOUND

class ProductNotFoundException : NotFoundException(PRODUCT_NOT_FOUND)
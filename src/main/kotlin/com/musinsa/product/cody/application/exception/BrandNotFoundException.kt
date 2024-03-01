package com.musinsa.product.cody.application.exception

import com.musinsa.product.cody.application.exception.ErrorCode.BRAND_NOT_FOUND

class BrandNotFoundException : NotFoundException(BRAND_NOT_FOUND)
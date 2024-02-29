package com.musinsa.product.search.application.exception

import com.musinsa.product.search.application.exception.ErrorCode.BRAND_NOT_FOUND

class BrandNotFoundException : NotFoundException(BRAND_NOT_FOUND)
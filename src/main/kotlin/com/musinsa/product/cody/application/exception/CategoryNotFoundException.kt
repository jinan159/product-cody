package com.musinsa.product.cody.application.exception

import com.musinsa.product.cody.application.exception.ErrorCode.CATEGORY_NOT_FOUND

class CategoryNotFoundException : NotFoundException(CATEGORY_NOT_FOUND)
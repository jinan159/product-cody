package com.musinsa.product.search.application.exception

import com.musinsa.product.search.application.exception.ErrorCode.CATEGORY_NOT_FOUND

class CategoryNotFoundException : NotFoundException(CATEGORY_NOT_FOUND)
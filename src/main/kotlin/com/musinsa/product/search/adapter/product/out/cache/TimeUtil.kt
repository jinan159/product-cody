package com.musinsa.product.search.adapter.product.out.cache

import java.time.Duration

val Int.minutes: Duration
    get() = Duration.ofMinutes(this.toLong())
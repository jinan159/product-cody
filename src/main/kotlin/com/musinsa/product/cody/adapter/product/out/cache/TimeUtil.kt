package com.musinsa.product.cody.adapter.product.out.cache

import java.time.Duration

val Int.minutes: Duration
    get() = Duration.ofMinutes(this.toLong())
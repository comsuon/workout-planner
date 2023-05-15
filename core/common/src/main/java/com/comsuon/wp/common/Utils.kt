package com.comsuon.wp.common

import androidx.lifecycle.MutableLiveData
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Random
import kotlin.math.abs

fun <T> MutableLiveData<T>.modifyValue(transform: T.() -> T) {
    this.value = this.value?.run(transform)
}

fun getUniqueId(): Long {
    return abs(Random(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)).nextLong())
}
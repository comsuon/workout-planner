package com.comsuon.wp.common

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.modifyValue(transform: T.() -> T) {
    this.value = this.value?.run(transform)
}
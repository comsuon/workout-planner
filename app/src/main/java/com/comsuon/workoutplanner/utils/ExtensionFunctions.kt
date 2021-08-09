package com.comsuon.workoutplanner.utils

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.modifyValue(transform: T.() -> T) {
    this.value = this.value?.run(transform)
}
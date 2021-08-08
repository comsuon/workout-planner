package com.comsuon.workoutplanner.viewmodel

sealed class UiState {
    object Loading : UiState()

    data class Success<out T>(val result: T) : UiState()

    data class Error(val errorCode: Int) : UiState()
}


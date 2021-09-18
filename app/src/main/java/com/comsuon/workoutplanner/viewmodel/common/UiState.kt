package com.comsuon.workoutplanner.viewmodel.common

sealed class UiState {
    object Empty : UiState()

    object Loading : UiState()

    data class Success<T>(val result: T) : UiState()

    data class Error<T : ErrorState>(val error: T) : UiState()
}


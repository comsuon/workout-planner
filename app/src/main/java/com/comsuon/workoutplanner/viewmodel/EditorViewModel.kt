package com.comsuon.workoutplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comsuon.workoutplanner.repository.WorkoutRepo
import com.comsuon.workoutplanner.view.ExerciseModel
import com.comsuon.workoutplanner.view.LoopModel
import com.comsuon.workoutplanner.view.WorkoutModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(private val repo: WorkoutRepo) : ViewModel() {
    private val _workoutData = MutableStateFlow(WorkoutModel())
    val workoutData: StateFlow<WorkoutModel> = _workoutData
    private val _uiState = MutableSharedFlow<UiState>()
    val uiState: SharedFlow<UiState> = _uiState

    fun addExerciseData(loopIndex: Int, exercise: ExerciseModel) {

    }

    fun addLoop(loop: LoopModel) {

    }

    fun saveWorkout() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
        }

    }
}
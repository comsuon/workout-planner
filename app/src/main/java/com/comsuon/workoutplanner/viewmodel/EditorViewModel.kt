package com.comsuon.workoutplanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comsuon.workoutplanner.repository.WorkoutRepo
import com.comsuon.workoutplanner.utils.modifyValue
import com.comsuon.workoutplanner.view.ExerciseModel
import com.comsuon.workoutplanner.view.LoopModel
import com.comsuon.workoutplanner.view.WorkoutModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(private val repo: WorkoutRepo) : ViewModel() {
    private val _workoutData = MutableLiveData(WorkoutModel())
    val workoutData: LiveData<WorkoutModel> = _workoutData
    private val _uiState = MutableSharedFlow<UiState>()
    val uiState: SharedFlow<UiState> = _uiState

    fun addExerciseData(loopIndex: Int, exercise: ExerciseModel) {

    }

    fun addLoop(loop: LoopModel) {

    }

    fun setWorkoutName(workoutName: String) {
        _workoutData.modifyValue { copy(workoutName = workoutName) }
    }

    fun saveWorkout() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
        }

    }
}
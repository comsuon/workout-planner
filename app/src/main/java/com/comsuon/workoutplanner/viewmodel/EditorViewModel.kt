package com.comsuon.workoutplanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comsuon.workoutplanner.repository.WorkoutRepo
import com.comsuon.workoutplanner.ui.theme.Blue
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

    fun setWorkoutName(workoutName: String) {
        _workoutData.modifyValue { copy(workoutName = workoutName) }
    }

    //use immutable list and then turn it to mutable list
    //Jetpack compose is not compatible with mutable list
    fun addEmptyLoop() {
        val newLoopModel = LoopModel(
            exerciseList = listOf(
                ExerciseModel(),
                ExerciseModel(
                    exerciseName = "Rest",
                    isTime = true,
                    timePerRep = 45,
                    colorCode = Blue
                )
            )
        )
        val newList = _workoutData.value?.loopList?.toMutableList()?.also { it.add(newLoopModel) }
            ?: listOf()
        _workoutData.modifyValue { copy(loopList = newList) }
    }

    fun setLoop(index: Int, loop: LoopModel) {
        val newList = _workoutData.value?.loopList?.toMutableList()?.also {
            it[index] = loop
        } ?: listOf()
        _workoutData.modifyValue { copy(loopList = newList) }
    }

    fun addEmptyExercise(loopIndex: Int) {
        val newLoopModel = _workoutData.value?.loopList?.getOrNull(loopIndex)?.copy() ?: LoopModel()
        newLoopModel.exerciseList = newLoopModel.exerciseList + listOf(ExerciseModel())
        setLoop(loopIndex, newLoopModel)
    }

    fun updateExercise(loopIndex: Int, exerciseIndex: Int, newExerciseModel: ExerciseModel) {
        val newLoopModel = _workoutData.value?.loopList?.getOrNull(loopIndex)?.copy() ?: LoopModel()
        val newList = newLoopModel.exerciseList.toMutableList()
        newList[exerciseIndex] = newExerciseModel
        newLoopModel.exerciseList = newList
        setLoop(loopIndex, newLoopModel)
    }

    fun deleteExercise(loopIndex: Int, exerciseIndex: Int) {
        val newLoopModel = _workoutData.value?.loopList?.getOrNull(loopIndex)?.copy() ?: LoopModel()
        val newList = newLoopModel.exerciseList.toMutableList().also {
            it.removeAt(exerciseIndex)
        }
        newLoopModel.exerciseList = newList
        setLoop(loopIndex, newLoopModel)
    }

    fun saveWorkout() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
        }

    }
}
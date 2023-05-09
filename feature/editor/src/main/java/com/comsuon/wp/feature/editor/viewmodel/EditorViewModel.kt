package com.comsuon.wp.feature.editor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comsuon.wp.common.modifyValue
import com.comsuon.wp.feature.editor.R
import com.comsuon.wp.model.ExerciseModel
import com.comsuon.wp.model.LoopModel
import com.comsuon.wp.model.WorkoutModel
import com.comsuon.wp.ui.model.ErrorState
import com.comsuon.wp.ui.model.Event
import com.comsuon.wp.ui.model.UiState
import com.wp.core.data.repository.WorkoutRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(private val repo: WorkoutRepo) : ViewModel() {
    //Workout data livedata
    private val _workoutData = MutableLiveData(WorkoutModel())
    val workoutData: LiveData<WorkoutModel> = _workoutData

    //UI state livedata
    private val _uiState = MutableLiveData<Event<UiState>>()
    val uiState: LiveData<Event<UiState>> = _uiState

    //list item scroll index livedata
    private val _scrollIndex = MutableLiveData<Event<Int>>()
    val scrollIndex: LiveData<Event<Int>> = _scrollIndex

    fun loadWorkout(id: String) {
        viewModelScope.launch {
            val workOutModel = try {
                withContext(Dispatchers.IO) {
                    repo.getWorkout(id.toInt())
                }
            } catch (e: Exception) {
                null
            }
            workOutModel?.let {
                _workoutData.postValue(it)
            }
        }
    }

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
                    colorCode = ""
                )
            )
        )
        val newList = _workoutData.value?.loopList?.toMutableList()?.also { it.add(newLoopModel) }
            ?: listOf()
        _workoutData.modifyValue { copy(loopList = newList) }
    }

    fun deleteLoop(loopIndex: Int) {
        val newList = _workoutData.value?.loopList?.toMutableList()?.also {
            it.removeAt(loopIndex)
        } ?: listOf()
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
        getValidationError()?.also { it ->
            _uiState.postValue(Event(UiState.Error(it)))
            if (it is EmptyExercise) {
                val loopIndex =
                    _workoutData.value?.loopList?.indexOfFirst { loopModel ->
                        loopModel.exerciseList.isNullOrEmpty()
                    } ?: 0
                _scrollIndex.postValue(
                    Event(loopIndex)
                )
            }
            return
        }
        viewModelScope.launch {
            _uiState.postValue(Event(UiState.Loading))
            delay(1500L)
            try {
                withContext(Dispatchers.IO) {
                    repo.saveWorkoutData(_workoutData.value!!)
                }
                _uiState.postValue(Event(UiState.Success(SaveWorkoutSuccess)))
            } catch (e: Exception) {
                _uiState.postValue(Event(UiState.Error(ErrorSavingExercise)))
            }
        }
    }

    private fun getValidationError(): ErrorState? {
        if (_workoutData.value?.workoutName.isNullOrEmpty())
            return WorkoutNameMissing

        if (_workoutData.value?.loopList.isNullOrEmpty())
            return EmptyLoop

        if (_workoutData.value?.loopList?.any { it.exerciseList.isEmpty() } == true)
            return EmptyExercise

        //else no error return null
        return null
    }
}

object SaveWorkoutSuccess
object WorkoutNameMissing : ErrorState(R.string.editor_error_workout_name_missing)
object EmptyLoop : ErrorState(R.string.editor_error_empty_loop)
object EmptyExercise : ErrorState(R.string.editor_error_empty_exercise_in_loop)
object ErrorSavingExercise : ErrorState(R.string.editor_error_empty_exercise_in_loop)
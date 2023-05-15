package com.comsuon.wp.feature.editor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comsuon.wp.common.getUniqueId
import com.comsuon.wp.common.modifyValue
import com.comsuon.wp.common.parseHexString
import com.comsuon.wp.common.randomExerciseColor
import com.comsuon.wp.core.domain.GetWorkoutUseCase
import com.comsuon.wp.core.domain.SaveWorkoutUseCase
import com.comsuon.wp.feature.editor.R
import com.comsuon.wp.model.ExerciseModel
import com.comsuon.wp.model.LoopModel
import com.comsuon.wp.model.WorkoutModel
import com.comsuon.wp.ui.model.ErrorState
import com.comsuon.wp.ui.model.Event
import com.comsuon.wp.ui.model.UiState
import com.comsuon.wp.ui.theme.Blue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val getWorkoutByIdUseCase: GetWorkoutUseCase,
    private val saveWorkoutUseCase: SaveWorkoutUseCase
) : ViewModel() {

    //Workout data livedata
    private val _workoutData = MutableLiveData(WorkoutModel())
    val workoutData: LiveData<WorkoutModel> = _workoutData

    //UI state livedata
    private val _uiState = MutableLiveData<Event<UiState>>()
    val uiState: LiveData<Event<UiState>> = _uiState

    //list item scroll index livedata
    private val _scrollIndex = MutableLiveData<Event<Int>>()
    val scrollIndex: LiveData<Event<Int>> = _scrollIndex

    fun loadWorkout(id: Long) {
        viewModelScope.launch {
            val workOutModel = try {
                withContext(Dispatchers.IO) {
                    getWorkoutByIdUseCase(id)
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
            loopId = getUniqueId(),
            exerciseList = listOf(
                ExerciseModel(
                    exerciseId = getUniqueId(),
                    colorCode = randomExerciseColor().parseHexString()
                ),
                ExerciseModel(
                    exerciseId = getUniqueId(),
                    exerciseName = "Rest",
                    isTime = true,
                    timePerRep = 45,
                    colorCode = Blue.parseHexString()
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
        newLoopModel.exerciseList =
            newLoopModel.exerciseList + listOf(
                ExerciseModel(
                    exerciseId = getUniqueId(),
                    colorCode = randomExerciseColor().parseHexString()
                )
            )
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

    fun reorderLoop(fromIndex: Int, toIndex: Int) {
        val newLoopList = _workoutData.value?.loopList?.toMutableList()?.also {
            Collections.swap(it, fromIndex, toIndex)
        }
        newLoopList?.let {
            _workoutData.modifyValue { copy(loopList = newLoopList) }
        }
    }

    fun reorderExercise(loopId: Int, fromIndex: Int, toIndex: Int) {
        val newLoopModel = _workoutData.value?.loopList?.getOrNull(loopId)?.copy()
        newLoopModel?.also {
            val newExerciseList = it.exerciseList.toMutableList()
            Collections.swap(newExerciseList, fromIndex, toIndex)
            newLoopModel.exerciseList = newExerciseList
            setLoop(loopId, it)
        }
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
                    saveWorkoutUseCase(workoutData.value!!)
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
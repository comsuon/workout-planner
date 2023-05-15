package com.comsuon.wp.collections.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comsuon.wp.core.domain.DeleteWorkoutUseCase
import com.comsuon.wp.core.domain.GetWorkoutListUseCase
import com.comsuon.wp.model.WorkoutModel
import com.comsuon.wp.ui.model.Event
import com.comsuon.wp.ui.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWorkoutListUseCase: GetWorkoutListUseCase,
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase
) : ViewModel() {

    private val _workoutList = MutableLiveData<List<WorkoutModel>>()
    private val _uiState = MutableLiveData<Event<UiState>>()
    val workoutList: LiveData<List<WorkoutModel>> = _workoutList
    val uiState: LiveData<Event<UiState>> = _uiState

    init {
        loadWorkoutList()
    }

    fun loadWorkoutList() {
        _uiState.postValue(Event(UiState.Loading))
        viewModelScope.launch {
            val workoutList = try {
                withContext(Dispatchers.IO) {
                    getWorkoutListUseCase()
                }
            } catch (e: Exception) {
                null
            }
            if (workoutList.isNullOrEmpty().not()) {
                _workoutList.postValue(workoutList)
            } else {
                _workoutList.postValue(emptyList())
            }
            _uiState.postValue(Event(UiState.Empty))
        }
    }

    fun deleteWorkout(index: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteWorkoutUseCase(index)
            }
            loadWorkoutList()
        }
    }

    fun addFavourite(index: Int) {

    }
}
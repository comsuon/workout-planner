package com.comsuon.workoutplanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comsuon.wp.model.WorkoutModel
import com.comsuon.workoutplanner.viewmodel.common.Event
import com.comsuon.workoutplanner.viewmodel.common.UiState
import com.wp.core.data.repository.WorkoutRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repo: WorkoutRepo) : ViewModel() {
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
                    repo.getWorkoutDataList()
                }
            } catch (e: Exception) {
                null
            }
            if (workoutList.isNullOrEmpty().not()) {
                _workoutList.postValue(workoutList)
            }
            _uiState.postValue(Event(UiState.Empty))
        }
    }

    fun deleteWorkout(index: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.deleteWorkout(index)
            }
            loadWorkoutList()
        }
    }

    fun addFavourite(index: Int) {

    }
}
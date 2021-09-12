package com.comsuon.workoutplanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comsuon.workoutplanner.repository.WorkoutRepo
import com.comsuon.workoutplanner.view.WorkoutModel
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
}
package com.wp.core.data.repository

import com.comsuon.wp.model.WorkoutModel

interface WorkoutRepo {
    suspend fun getWorkoutDataList(): List<WorkoutModel>
    suspend fun saveWorkoutData(data: WorkoutModel)
    suspend fun getWorkout(id: Int): WorkoutModel
    suspend fun deleteWorkout(id: Int)
}
package com.comsuon.workoutplanner.repository

import com.comsuon.workoutplanner.view.WorkoutModel

interface WorkoutRepo {
    suspend fun getWorkoutDataList(): List<WorkoutModel>
    suspend fun saveWorkoutData(data: WorkoutModel)
    suspend fun getWorkout(id: Int): WorkoutModel
    suspend fun deleteWorkout(id: Int)
}
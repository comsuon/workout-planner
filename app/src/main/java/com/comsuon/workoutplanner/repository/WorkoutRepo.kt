package com.comsuon.workoutplanner.repository

import com.comsuon.workoutplanner.repository.db.pojo.WorkoutData
import com.comsuon.workoutplanner.view.WorkoutModel

interface WorkoutRepo {
    suspend fun getWorkoutDataList(): List<WorkoutModel>
    suspend fun saveWorkoutData(data: WorkoutModel)
}
package com.comsuon.workoutplanner.repository

import com.comsuon.workoutplanner.repository.db.pojo.WorkoutData

interface WorkoutRepo {
    suspend fun getWorkoutDataList(): List<WorkoutData>
}
package com.comsuon.workoutplanner.repository

import com.comsuon.workoutplanner.repository.db.WorkoutDB
import com.comsuon.workoutplanner.repository.db.pojo.WorkoutData

class WorkoutRepoImpl constructor(
    private val appDB: WorkoutDB
) : WorkoutRepo {
    override suspend fun getWorkoutDataList(): List<WorkoutData> {
        return appDB.workoutDao().getWorkoutDataList()
    }
}
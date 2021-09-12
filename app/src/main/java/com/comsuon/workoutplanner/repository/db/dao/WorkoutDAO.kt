package com.comsuon.workoutplanner.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.comsuon.workoutplanner.repository.db.entities.WorkoutEntity
import com.comsuon.workoutplanner.repository.db.pojo.WorkoutData

@Dao
interface WorkoutDAO {

    @Transaction
    @Query("SELECT * FROM workout")
    fun getWorkoutDataList(): List<WorkoutData>

    @Transaction
    @Insert
    fun insertWorkout(workoutEntity: WorkoutEntity): Long
}
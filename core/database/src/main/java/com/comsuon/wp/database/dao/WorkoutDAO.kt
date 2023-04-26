package com.comsuon.wp.database.dao

import androidx.room.*
import com.comsuon.wp.database.model.WorkoutData
import com.comsuon.wp.database.model.WorkoutEntity

@Dao
interface WorkoutDAO {

    @Transaction
    @Query("SELECT * FROM workout")
    fun getWorkoutDataList(): List<WorkoutData>

    @Transaction
    @Query("SELECT * FROM workout WHERE id=:id")
    fun getWorkoutById(id: Int): WorkoutData

    @Transaction
    @Insert
    fun insertWorkout(workoutEntity: WorkoutEntity): Long

    @Transaction
    @Delete
    fun deleteWorkout(workoutData: WorkoutEntity)
}
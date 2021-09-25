package com.comsuon.workoutplanner.repository.db.dao

import androidx.room.*
import com.comsuon.workoutplanner.repository.db.entities.WorkoutEntity
import com.comsuon.workoutplanner.repository.db.pojo.WorkoutData

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
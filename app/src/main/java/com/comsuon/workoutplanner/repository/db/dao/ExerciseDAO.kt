package com.comsuon.workoutplanner.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.comsuon.workoutplanner.repository.db.entities.ExerciseEntity

@Dao
interface ExerciseDAO {
    @Transaction
    @Insert
    fun insertAllExercise(exercises: List<ExerciseEntity>)
}
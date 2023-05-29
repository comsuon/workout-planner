package com.comsuon.wp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import com.comsuon.wp.database.model.ExerciseEntity

@Dao
interface ExerciseDAO {
    @Transaction
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAllExercise(exercises: List<ExerciseEntity>)

    @Transaction
    @Delete
    fun deleteExercise(exercise: ExerciseEntity)
}
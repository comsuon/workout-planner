package com.comsuon.wp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.comsuon.wp.database.model.ExerciseEntity

@Dao
interface ExerciseDAO {
    @Transaction
    @Insert
    fun insertAllExercise(exercises: List<ExerciseEntity>)
}
package com.comsuon.workoutplanner.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.comsuon.workoutplanner.repository.db.dao.WorkoutDAO
import com.comsuon.workoutplanner.repository.db.entities.ExerciseEntity
import com.comsuon.workoutplanner.repository.db.entities.LoopEntity
import com.comsuon.workoutplanner.repository.db.entities.WorkoutEntity

@Database(entities = [WorkoutEntity::class, LoopEntity::class, ExerciseEntity::class], version = 1)
abstract class WorkoutDB : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDAO
}
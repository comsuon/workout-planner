package com.comsuon.workoutplanner.repository.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.comsuon.workoutplanner.repository.db.dao.ExerciseDAO
import com.comsuon.workoutplanner.repository.db.dao.LoopDAO
import com.comsuon.workoutplanner.repository.db.dao.WorkoutDAO
import com.comsuon.workoutplanner.repository.db.entities.ExerciseEntity
import com.comsuon.workoutplanner.repository.db.entities.LoopEntity
import com.comsuon.workoutplanner.repository.db.entities.WorkoutEntity

@Database(
    entities = [WorkoutEntity::class, LoopEntity::class, ExerciseEntity::class],
    version = 2,
    exportSchema = false
)
abstract class WorkoutDB : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDAO
    abstract fun loopDao(): LoopDAO
    abstract fun exerciseDao(): ExerciseDAO
}
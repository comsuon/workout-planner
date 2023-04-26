package com.comsuon.wp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.comsuon.wp.database.dao.ExerciseDAO
import com.comsuon.wp.database.dao.LoopDAO
import com.comsuon.wp.database.dao.WorkoutDAO
import com.comsuon.wp.database.model.ExerciseEntity
import com.comsuon.wp.database.model.LoopEntity
import com.comsuon.wp.database.model.WorkoutEntity

@Database(
    entities = [WorkoutEntity::class, LoopEntity::class, ExerciseEntity::class],
    version = 2,
    exportSchema = false
) abstract class WorkoutDB : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDAO
    abstract fun loopDao(): LoopDAO
    abstract fun exerciseDao(): ExerciseDAO
}
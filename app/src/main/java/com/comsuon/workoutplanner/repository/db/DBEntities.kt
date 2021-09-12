package com.comsuon.workoutplanner.repository.db

import com.comsuon.workoutplanner.repository.db.entities.ExerciseEntity
import com.comsuon.workoutplanner.repository.db.entities.LoopEntity
import com.comsuon.workoutplanner.repository.db.entities.WorkoutEntity

fun arrayEntities() = arrayOf(
    WorkoutEntity::class,
    LoopEntity::class,
    ExerciseEntity::class
)

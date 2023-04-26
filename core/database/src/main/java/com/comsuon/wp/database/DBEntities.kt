package com.comsuon.wp.database

import com.comsuon.wp.database.model.ExerciseEntity
import com.comsuon.wp.database.model.LoopEntity
import com.comsuon.wp.database.model.WorkoutEntity

object DBEntities {
    fun arrayEntities() = arrayOf(
        WorkoutEntity::class,
        LoopEntity::class,
        ExerciseEntity::class
    )
}

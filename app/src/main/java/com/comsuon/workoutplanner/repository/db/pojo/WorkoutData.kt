package com.comsuon.workoutplanner.repository.db.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.comsuon.workoutplanner.repository.db.entities.LoopEntity
import com.comsuon.workoutplanner.repository.db.entities.WorkoutEntity

data class WorkoutData(
    @Embedded val workoutEntity: WorkoutEntity,

    @Relation(
        entity = LoopEntity::class,
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    val listOfLoop: List<LoopExercises>
)

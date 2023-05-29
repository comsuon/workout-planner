package com.comsuon.wp.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.comsuon.wp.model.WorkoutModel

data class WorkoutData(
    @Embedded val workoutEntity: WorkoutEntity,

    @Relation(
        entity = LoopEntity::class,
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    val listOfLoop: List<LoopExercises>
)

fun WorkoutData.toModel(): WorkoutModel {
    return WorkoutModel(
        workoutEntity.id,
        workoutEntity.name,
        loopList = listOfLoop.map(LoopExercises::toModel).sortedBy { it.indexInWorkout },
        isFavourite = workoutEntity.isFavourite
    )
}
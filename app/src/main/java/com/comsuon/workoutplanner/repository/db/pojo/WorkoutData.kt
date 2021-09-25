package com.comsuon.workoutplanner.repository.db.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.comsuon.workoutplanner.repository.db.entities.LoopEntity
import com.comsuon.workoutplanner.repository.db.entities.WorkoutEntity
import com.comsuon.workoutplanner.view.WorkoutModel

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
        loopList = listOfLoop.map(LoopExercises::toModel),
        isFavourite = workoutEntity.isFavourite
    )
}
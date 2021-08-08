package com.comsuon.workoutplanner.repository.db.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.comsuon.workoutplanner.repository.db.entities.ExerciseEntity
import com.comsuon.workoutplanner.repository.db.entities.LoopEntity

data class LoopExercises(
    @Embedded val loop: LoopEntity,

    @Relation(
        parentColumn = "loopId",
        entityColumn = "loopId"
    )
    val listExercises: List<ExerciseEntity>
)
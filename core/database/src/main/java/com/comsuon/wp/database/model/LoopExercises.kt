package com.comsuon.wp.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.comsuon.wp.model.ExerciseModel
import com.comsuon.wp.model.LoopModel

data class LoopExercises(
    @Embedded val loop: LoopEntity,

    @Relation(
        parentColumn = "loopId",
        entityColumn = "loopId"
    )
    val listExercises: List<ExerciseEntity>
)

fun LoopExercises.toModel(): LoopModel {
    val loopModel = LoopModel(
        loopId = loop.loopId, setCount = loop.setCount,
        indexInWorkout = loop.indexInWorkout
    )
    val listExercises = listExercises.map { exerciseEntity ->
        ExerciseModel(
            exerciseId = exerciseEntity.exerciseId,
            exerciseName = exerciseEntity.exerciseName,
            repCount = exerciseEntity.repCount,
            timePerRep = exerciseEntity.timePerRep,
            isTime = exerciseEntity.isTime,
            autoFinished = exerciseEntity.autoFinished,
            skipLastSet = exerciseEntity.skipLastSet,
            indexInLoop = exerciseEntity.indexInLoop,
            colorCode = exerciseEntity.colorCode
        )
    }.sortedBy { it.indexInLoop }
    loopModel.exerciseList = listExercises
    return loopModel
}
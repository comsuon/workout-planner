package com.comsuon.workoutplanner.repository.db.pojo

import androidx.compose.ui.graphics.Color
import androidx.room.Embedded
import androidx.room.Relation
import com.comsuon.workoutplanner.repository.db.entities.ExerciseEntity
import com.comsuon.workoutplanner.repository.db.entities.LoopEntity
import com.comsuon.workoutplanner.view.ExerciseModel
import com.comsuon.workoutplanner.view.LoopModel

data class LoopExercises(
    @Embedded val loop: LoopEntity,

    @Relation(
        parentColumn = "loopId",
        entityColumn = "loopId"
    )
    val listExercises: List<ExerciseEntity>
)

fun LoopExercises.toModel(): LoopModel {
    val loopModel = LoopModel(setCount = loop.setCount)
    val listExercises = listExercises.map { exerciseEntity ->
        ExerciseModel(
            exerciseName = exerciseEntity.exerciseName,
            repCount = exerciseEntity.repCount,
            timePerRep = exerciseEntity.timePerRep,
            isTime = exerciseEntity.isTime,
            autoFinished = exerciseEntity.autoFinished,
            skipLastSet = exerciseEntity.skipLastSet,
            colorCode = Color(exerciseEntity.colorCode.toULong())
        )
    }
    loopModel.exerciseList = listExercises
    return loopModel
}
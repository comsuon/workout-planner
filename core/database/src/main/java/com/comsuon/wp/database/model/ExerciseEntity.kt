package com.comsuon.wp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.comsuon.wp.model.ExerciseModel

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Long = 0,
    val loopId: Long,
    val exerciseName: String,
    val isTime: Boolean,
    val repCount: Int,
    val timePerRep: Int,
    val autoFinished: Boolean,
    val skipLastSet: Boolean,
    val indexInLoop: Int,
    val colorCode: String
)

fun ExerciseModel.toEntity(loopId: Long): ExerciseEntity {
    return ExerciseEntity(
        exerciseId = exerciseId,
        loopId = loopId,
        exerciseName = exerciseName,
        isTime = isTime,
        repCount = repCount,
        timePerRep = timePerRep,
        autoFinished = autoFinished,
        skipLastSet = skipLastSet,
        colorCode = colorCode,
        indexInLoop = indexInLoop
    )
}
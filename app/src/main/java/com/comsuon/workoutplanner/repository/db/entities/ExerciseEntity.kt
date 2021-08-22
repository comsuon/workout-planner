package com.comsuon.workoutplanner.repository.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey
    val exerciseId: Int,
    val loopId: Int,
    val exerciseName: String,
    val repCount: Int,
    val timePerRep: Long,
    val autoFinished: Boolean,
    val skipLastSet: Boolean,
    val colorCode: Long
)
package com.comsuon.wp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Int = 0,
    val loopId: Int,
    val exerciseName: String,
    val isTime: Boolean,
    val repCount: Int,
    val timePerRep: Int,
    val autoFinished: Boolean,
    val skipLastSet: Boolean,
    val colorCode: String
)
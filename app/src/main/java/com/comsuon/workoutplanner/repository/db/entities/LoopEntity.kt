package com.comsuon.workoutplanner.repository.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loop")
data class LoopEntity(
    @PrimaryKey(autoGenerate = true) val loopId: Int = 0,
    val workoutId: Int,
    val setCount: Int,
)
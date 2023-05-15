package com.comsuon.wp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loop")
data class LoopEntity(
    @PrimaryKey(autoGenerate = true) val loopId: Long = 0,
    val workoutId: Int,
    val setCount: Int,
)
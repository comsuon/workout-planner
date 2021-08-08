package com.comsuon.workoutplanner.repository.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "loop")
data class LoopEntity(
    @PrimaryKey val loopId: Int,
    val workoutId: Int,
    val setCount: Int,
    val loopName: String?,
)
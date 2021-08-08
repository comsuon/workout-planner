package com.comsuon.workoutplanner.repository.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class WorkoutEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)

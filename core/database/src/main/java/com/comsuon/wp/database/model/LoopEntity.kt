package com.comsuon.wp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.comsuon.wp.model.LoopModel

@Entity(tableName = "loop")
data class LoopEntity(
    @PrimaryKey(autoGenerate = true) val loopId: Long = 0,
    val workoutId: Long,
    val setCount: Int,
    val indexInWorkout: Int
)

fun LoopModel.toLoopEntity(workoutId: Long): LoopEntity {
    return LoopEntity(
        loopId = loopId,
        workoutId = workoutId,
        setCount = setCount,
        indexInWorkout = indexInWorkout
    )
}
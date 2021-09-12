package com.comsuon.workoutplanner.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.comsuon.workoutplanner.repository.db.entities.LoopEntity

@Dao
interface LoopDAO {
    @Transaction
    @Insert
    fun insertLoop(loopEntity: LoopEntity): Long
}
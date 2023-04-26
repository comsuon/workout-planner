package com.comsuon.wp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.comsuon.wp.database.model.LoopEntity

@Dao
interface LoopDAO {
    @Transaction
    @Insert
    fun insertLoop(loopEntity: LoopEntity): Long
}
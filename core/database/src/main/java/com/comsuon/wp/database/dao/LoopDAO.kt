package com.comsuon.wp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.comsuon.wp.database.model.LoopEntity

@Dao
interface LoopDAO {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoop(loopEntity: LoopEntity): Long

    @Transaction
    @Delete
    fun deleteLoop(loopEntity: LoopEntity)
}
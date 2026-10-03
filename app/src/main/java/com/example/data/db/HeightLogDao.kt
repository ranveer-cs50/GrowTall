package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HeightLogDao {
    @Query("SELECT * FROM height_logs ORDER BY timestamp ASC")
    fun getAllHeightLogs(): Flow<List<HeightLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeight(record: HeightLogEntity): Long

    @Query("DELETE FROM height_logs WHERE id = :id")
    suspend fun deleteById(id: Long)
}

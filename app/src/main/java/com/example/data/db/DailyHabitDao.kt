package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyHabitDao {
    @Query("SELECT * FROM daily_habits WHERE dateString = :date LIMIT 1")
    fun getHabitForDate(date: String): Flow<DailyHabitEntity?>

    @Query("SELECT * FROM daily_habits ORDER BY dateString DESC LIMIT 30")
    fun getRecentHabits(): Flow<List<DailyHabitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(habit: DailyHabitEntity)
}

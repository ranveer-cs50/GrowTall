package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealLogDao {
    @Query("SELECT * FROM meal_logs WHERE dateString = :date ORDER BY timestamp DESC")
    fun getMealsForDate(date: String): Flow<List<MealLogEntity>>

    @Query("SELECT * FROM meal_logs ORDER BY timestamp DESC LIMIT 100")
    fun getAllRecentMeals(): Flow<List<MealLogEntity>>

    @Query("SELECT * FROM meal_logs WHERE timestamp >= :sinceTimestamp ORDER BY timestamp ASC")
    fun getMealsSince(sinceTimestamp: Long): Flow<List<MealLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealLogEntity): Long

    @Query("DELETE FROM meal_logs WHERE id = :id")
    suspend fun deleteMealById(id: Long)

    @Query("DELETE FROM meal_logs")
    suspend fun clearAll()
}

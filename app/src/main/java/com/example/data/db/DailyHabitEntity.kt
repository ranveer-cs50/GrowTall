package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_habits")
data class DailyHabitEntity(
    @PrimaryKey
    val dateString: String, // "YYYY-MM-DD"
    val sleepHours: Float = 8.5f,
    val waterGlasses: Int = 6,
    val exerciseMinutes: Int = 30
)

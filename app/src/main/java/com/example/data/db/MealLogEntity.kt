package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_logs")
data class MealLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateString: String, // e.g. "2026-10-03"
    val mealType: String,   // Breakfast, Lunch, Dinner, Snack
    val foodName: String,
    val proteinG: Float,
    val calciumMg: Float,
    val sugarG: Float,
    val sodiumMg: Float,
    val caloriesKcal: Int,
    val growthScore: Int,
    val timestamp: Long = System.currentTimeMillis()
)

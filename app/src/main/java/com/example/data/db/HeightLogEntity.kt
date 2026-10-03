package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "height_logs")
data class HeightLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateString: String,
    val heightCm: Float,
    val timestamp: Long = System.currentTimeMillis(),
    val note: String = ""
)

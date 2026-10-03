package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import com.example.data.api.GeminiFoodService
import com.example.data.datasource.GrowthNutritionPresetData
import com.example.data.db.AppDatabase
import com.example.data.db.DailyHabitEntity
import com.example.data.db.HeightLogEntity
import com.example.data.db.MealLogEntity
import com.example.data.model.BiologicalSex
import com.example.data.model.FoodScanResult
import com.example.data.model.GrowthAnalytics
import com.example.data.model.HeightRecord
import com.example.data.model.MealItem
import com.example.data.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NutritionRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val mealDao = db.mealLogDao()
    private val heightDao = db.heightLogDao()
    private val habitDao = db.dailyHabitDao()
    private val geminiService = GeminiFoodService()

    private val prefs: SharedPreferences =
        context.getSharedPreferences("growtall_user_profile", Context.MODE_PRIVATE)

    fun getUserProfile(): UserProfile {
        val name = prefs.getString("user_name", "Alex") ?: "Alex"
        val age = prefs.getInt("user_age", 14)
        val sexStr = prefs.getString("user_sex", BiologicalSex.MALE.name) ?: BiologicalSex.MALE.name
        val sex = try { BiologicalSex.valueOf(sexStr) } catch (e: Exception) { BiologicalSex.MALE }
        val currentHeight = prefs.getFloat("user_height", 163.5f)
        val targetHeight = prefs.getFloat("user_target_height", 182.0f)
        val currentWeight = prefs.getFloat("user_weight", 52.0f)
        val targetProtein = prefs.getInt("user_protein_target", 75)
        val targetCalcium = prefs.getInt("user_calcium_target", 1300)
        val maxSugar = prefs.getInt("user_max_sugar", 25)
        val maxSodium = prefs.getInt("user_max_sodium", 2200)
        val sleepHours = prefs.getFloat("user_target_sleep", 9.0f)
        val reminderHour = prefs.getInt("user_reminder_hour", 21)
        val reminderMinute = prefs.getInt("user_reminder_minute", 30)
        val notifsEnabled = prefs.getBoolean("user_notifs_enabled", true)

        return UserProfile(
            name = name,
            age = age,
            biologicalSex = sex,
            currentHeightCm = currentHeight,
            targetHeightCm = targetHeight,
            currentWeightKg = currentWeight,
            targetProteinG = targetProtein,
            targetCalciumMg = targetCalcium,
            maxSugarG = maxSugar,
            maxSodiumMg = maxSodium,
            targetSleepHours = sleepHours,
            bedtimeReminderHour = reminderHour,
            bedtimeReminderMinute = reminderMinute,
            notificationsEnabled = notifsEnabled
        )
    }

    fun saveUserProfile(profile: UserProfile) {
        prefs.edit().apply {
            putString("user_name", profile.name)
            putInt("user_age", profile.age)
            putString("user_sex", profile.biologicalSex.name)
            putFloat("user_height", profile.currentHeightCm)
            putFloat("user_target_height", profile.targetHeightCm)
            putFloat("user_weight", profile.currentWeightKg)
            putInt("user_protein_target", profile.targetProteinG)
            putInt("user_calcium_target", profile.targetCalciumMg)
            putInt("user_max_sugar", profile.maxSugarG)
            putInt("user_max_sodium", profile.maxSodiumMg)
            putFloat("user_target_sleep", profile.targetSleepHours)
            putInt("user_reminder_hour", profile.bedtimeReminderHour)
            putInt("user_reminder_minute", profile.bedtimeReminderMinute)
            putBoolean("user_notifs_enabled", profile.notificationsEnabled)
            apply()
        }
    }

    fun getTodayDateString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getMealsForDate(date: String = getTodayDateString()): Flow<List<MealItem>> {
        return mealDao.getMealsForDate(date).map { entities ->
            entities.map {
                MealItem(
                    id = it.id,
                    dateString = it.dateString,
                    mealType = it.mealType,
                    foodName = it.foodName,
                    proteinG = it.proteinG,
                    calciumMg = it.calciumMg,
                    sugarG = it.sugarG,
                    sodiumMg = it.sodiumMg,
                    caloriesKcal = it.caloriesKcal,
                    growthScore = it.growthScore,
                    timestamp = it.timestamp
                )
            }
        }
    }

    fun getAllHeightRecords(): Flow<List<HeightRecord>> {
        return heightDao.getAllHeightLogs().map { entities ->
            entities.map {
                HeightRecord(
                    id = it.id,
                    dateString = it.dateString,
                    heightCm = it.heightCm,
                    timestamp = it.timestamp,
                    note = it.note
                )
            }
        }
    }

    fun getDailyHabit(date: String = getTodayDateString()): Flow<DailyHabitEntity?> {
        return habitDao.getHabitForDate(date)
    }

    suspend fun saveDailyHabit(habit: DailyHabitEntity) = withContext(Dispatchers.IO) {
        habitDao.insertOrUpdate(habit)
    }

    suspend fun logMeal(
        mealType: String,
        foodName: String,
        proteinG: Float,
        calciumMg: Float,
        sugarG: Float,
        sodiumMg: Float,
        caloriesKcal: Int,
        growthScore: Int,
        date: String = getTodayDateString()
    ): Long = withContext(Dispatchers.IO) {
        mealDao.insertMeal(
            MealLogEntity(
                dateString = date,
                mealType = mealType,
                foodName = foodName,
                proteinG = proteinG,
                calciumMg = calciumMg,
                sugarG = sugarG,
                sodiumMg = sodiumMg,
                caloriesKcal = caloriesKcal,
                growthScore = growthScore,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun deleteMeal(id: Long) = withContext(Dispatchers.IO) {
        mealDao.deleteMealById(id)
    }

    suspend fun logHeight(heightCm: Float, note: String = ""): Long = withContext(Dispatchers.IO) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.format(Date())
        val currentProfile = getUserProfile()
        saveUserProfile(currentProfile.copy(currentHeightCm = heightCm))
        heightDao.insertHeight(
            HeightLogEntity(
                dateString = date,
                heightCm = heightCm,
                timestamp = System.currentTimeMillis(),
                note = note
            )
        )
    }

    suspend fun deleteHeightRecord(id: Long) = withContext(Dispatchers.IO) {
        heightDao.deleteById(id)
    }

    suspend fun analyzeFood(bitmap: Bitmap?, query: String?): FoodScanResult = withContext(Dispatchers.IO) {
        val profile = getUserProfile()
        geminiService.analyzeFood(
            bitmap = bitmap,
            foodDescription = query,
            userAge = profile.age,
            userSex = profile.biologicalSex.name
        )
    }

    suspend fun computeGrowthAnalytics(): GrowthAnalytics = withContext(Dispatchers.IO) {
        val profile = getUserProfile()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val weekAgoTimestamp = calendar.timeInMillis
        val weeklyMeals = mealDao.getMealsSince(weekAgoTimestamp).firstOrNull() ?: emptyList()

        val totalProtein = weeklyMeals.sumOf { it.proteinG.toDouble() }.toFloat()
        val totalCalcium = weeklyMeals.sumOf { it.calciumMg.toDouble() }.toFloat()
        val totalSugar = weeklyMeals.sumOf { it.sugarG.toDouble() }.toFloat()
        val totalSodium = weeklyMeals.sumOf { it.sodiumMg.toDouble() }.toFloat()

        val daysCount = 7f
        val avgProtein = totalProtein / daysCount
        val avgCalcium = totalCalcium / daysCount
        val avgSugar = totalSugar / daysCount
        val avgSodium = totalSodium / daysCount

        val cdcPercentile = GrowthNutritionPresetData.estimatePercentile(
            profile.biologicalSex,
            profile.age,
            profile.currentHeightCm
        )

        val predictedAdultHeight = GrowthNutritionPresetData.predictAdultHeight(
            profile.biologicalSex,
            profile.age,
            profile.currentHeightCm,
            profile.currentWeightKg
        )

        // Annual growth velocity calculation
        val allHeights = heightDao.getAllHeightLogs().firstOrNull() ?: emptyList()
        val velocity = if (allHeights.size >= 2) {
            val oldest = allHeights.first()
            val newest = allHeights.last()
            val daysDiff = ((newest.timestamp - oldest.timestamp) / (1000 * 60 * 60 * 24)).coerceAtLeast(1)
            val cmDiff = (newest.heightCm - oldest.heightCm).coerceAtLeast(0f)
            (cmDiff / daysDiff) * 365.25f
        } else {
            // Standard pubertal velocity estimate (6.5 to 9.5 cm/yr)
            if (profile.age in 12..15) 8.2f else 6.0f
        }

        // HGH Optimization score: based on protein consistency, low sugar, low sodium
        val proteinRatio = (avgProtein / profile.targetProteinG.coerceAtLeast(1)).coerceIn(0f, 1f)
        val calciumRatio = (avgCalcium / profile.targetCalciumMg.coerceAtLeast(1)).coerceIn(0f, 1f)
        val sugarPenalty = if (avgSugar > profile.maxSugarG) ((avgSugar - profile.maxSugarG) / profile.maxSugarG).coerceIn(0f, 0.4f) else 0f
        val sodiumPenalty = if (avgSodium > profile.maxSodiumMg) ((avgSodium - profile.maxSodiumMg) / profile.maxSodiumMg).coerceIn(0f, 0.3f) else 0f

        val hghScore = (((proteinRatio * 0.5f + calciumRatio * 0.5f) - sugarPenalty - sodiumPenalty) * 100).toInt().coerceIn(35, 98)

        GrowthAnalytics(
            currentHeightCm = profile.currentHeightCm,
            targetHeightCm = profile.targetHeightCm,
            estimatedAdultHeightCm = predictedAdultHeight,
            annualGrowthVelocityCm = velocity,
            cdcPercentile = cdcPercentile,
            weeklyAvgProteinG = avgProtein,
            weeklyAvgCalciumMg = avgCalcium,
            weeklyAvgSugarG = avgSugar,
            weeklyAvgSodiumMg = avgSodium,
            weeklyAvgSleepHours = profile.targetSleepHours,
            hghOptimizationScore = hghScore
        )
    }

    suspend fun seedInitialDemoDataIfEmpty() = withContext(Dispatchers.IO) {
        val existingHeights = heightDao.getAllHeightLogs().firstOrNull()
        if (existingHeights.isNullOrEmpty()) {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            // Seed historical heights over the past 6 months to showcase puberty velocity
            cal.add(Calendar.MONTH, -6)
            heightDao.insertHeight(HeightLogEntity(dateString = sdf.format(cal.time), heightCm = 159.2f, timestamp = cal.timeInMillis, note = "Start of growth tracking"))

            cal.add(Calendar.MONTH, 2)
            heightDao.insertHeight(HeightLogEntity(dateString = sdf.format(cal.time), heightCm = 160.8f, timestamp = cal.timeInMillis, note = "Puberty growth check"))

            cal.add(Calendar.MONTH, 2)
            heightDao.insertHeight(HeightLogEntity(dateString = sdf.format(cal.time), heightCm = 162.1f, timestamp = cal.timeInMillis, note = "High protein diet kick-off"))

            cal.add(Calendar.MONTH, 2)
            heightDao.insertHeight(HeightLogEntity(dateString = sdf.format(Date()), heightCm = 163.5f, timestamp = System.currentTimeMillis(), note = "Current measurement"))
        }

        val todayDate = getTodayDateString()
        val todayMeals = mealDao.getMealsForDate(todayDate).firstOrNull()
        if (todayMeals.isNullOrEmpty()) {
            // Seed today's sample breakfast and lunch
            mealDao.insertMeal(
                MealLogEntity(
                    dateString = todayDate,
                    mealType = "Breakfast",
                    foodName = "Greek Yogurt with Berries & Chia",
                    proteinG = 22.0f,
                    calciumMg = 340.0f,
                    sugarG = 7.0f,
                    sodiumMg = 75.0f,
                    caloriesKcal = 210,
                    growthScore = 10,
                    timestamp = System.currentTimeMillis() - 14400000
                )
            )
            mealDao.insertMeal(
                MealLogEntity(
                    dateString = todayDate,
                    mealType = "Lunch",
                    foodName = "Grilled Chicken Breast & Broccoli",
                    proteinG = 42.0f,
                    calciumMg = 110.0f,
                    sugarG = 1.5f,
                    sodiumMg = 280.0f,
                    caloriesKcal = 310,
                    growthScore = 10,
                    timestamp = System.currentTimeMillis() - 7200000
                )
            )

            // Seed today's habit
            habitDao.insertOrUpdate(
                DailyHabitEntity(
                    dateString = todayDate,
                    sleepHours = 9.0f,
                    waterGlasses = 6,
                    exerciseMinutes = 45
                )
            )
        }
    }
}

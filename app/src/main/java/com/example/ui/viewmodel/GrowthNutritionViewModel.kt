package com.example.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.GrowthNutritionPresetData
import com.example.data.db.DailyHabitEntity
import com.example.data.model.FoodScanResult
import com.example.data.model.GrowthAnalytics
import com.example.data.model.HeightRecord
import com.example.data.model.MealItem
import com.example.data.model.UserProfile
import com.example.data.repository.NutritionRepository
import com.example.util.NotificationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GrowthNutritionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NutritionRepository(application)

    private val _userProfile = MutableStateFlow(repository.getUserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    val todayMeals: StateFlow<List<MealItem>> = repository.getMealsForDate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val heightRecords: StateFlow<List<HeightRecord>> = repository.getAllHeightRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dailyHabit: StateFlow<DailyHabitEntity?> = repository.getDailyHabit()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _growthAnalytics = MutableStateFlow<GrowthAnalytics?>(null)
    val growthAnalytics: StateFlow<GrowthAnalytics?> = _growthAnalytics.asStateFlow()

    // Scanner state
    private val _scanResult = MutableStateFlow<FoodScanResult?>(null)
    val scanResult: StateFlow<FoodScanResult?> = _scanResult.asStateFlow()

    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()

    private val _capturedBitmap = MutableStateFlow<Bitmap?>(null)
    val capturedBitmap: StateFlow<Bitmap?> = _capturedBitmap.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    init {
        NotificationHelper.createNotificationChannel(application)
        viewModelScope.launch {
            repository.seedInitialDemoDataIfEmpty()
            refreshAnalytics()
            // Set initial scan result to a rich superfood demonstration
            _scanResult.value = GrowthNutritionPresetData.VERIFIED_FOOD_DATABASE[0]
        }
    }

    fun refreshAnalytics() {
        viewModelScope.launch {
            _growthAnalytics.value = repository.computeGrowthAnalytics()
        }
    }

    fun analyzeFood(bitmap: Bitmap?, query: String? = null) {
        _isAnalyzing.value = true
        _capturedBitmap.value = bitmap
        _statusMessage.value = null
        viewModelScope.launch {
            try {
                val result = repository.analyzeFood(bitmap, query)
                _scanResult.value = result
                _statusMessage.value = "Analysis complete for ${result.foodName}"
            } catch (e: Exception) {
                _statusMessage.value = "Analysis error: ${e.localizedMessage ?: "Unknown error"}"
            } finally {
                _isAnalyzing.value = false
                refreshAnalytics()
            }
        }
    }

    fun selectPresetFood(preset: FoodScanResult) {
        _scanResult.value = preset
        _capturedBitmap.value = null
        _statusMessage.value = "Loaded ${preset.foodName}"
    }

    fun logCurrentScanMeal(mealType: String) {
        val current = _scanResult.value ?: return
        viewModelScope.launch {
            repository.logMeal(
                mealType = mealType,
                foodName = current.foodName,
                proteinG = current.proteinG,
                calciumMg = current.calciumMg,
                sugarG = current.sugarG,
                sodiumMg = current.sodiumMg,
                caloriesKcal = current.caloriesKcal,
                growthScore = current.heightGrowthScore
            )
            _statusMessage.value = "Added to today's $mealType!"
            refreshAnalytics()
        }
    }

    fun logCustomMeal(
        mealType: String,
        name: String,
        proteinG: Float,
        calciumMg: Float,
        sugarG: Float,
        sodiumMg: Float,
        caloriesKcal: Int,
        growthScore: Int
    ) {
        viewModelScope.launch {
            repository.logMeal(
                mealType = mealType,
                foodName = name,
                proteinG = proteinG,
                calciumMg = calciumMg,
                sugarG = sugarG,
                sodiumMg = sodiumMg,
                caloriesKcal = caloriesKcal,
                growthScore = growthScore
            )
            refreshAnalytics()
        }
    }

    fun deleteMeal(id: Long) {
        viewModelScope.launch {
            repository.deleteMeal(id)
            refreshAnalytics()
        }
    }

    fun logHeight(heightCm: Float, note: String = "") {
        viewModelScope.launch {
            repository.logHeight(heightCm, note)
            val updated = _userProfile.value.copy(currentHeightCm = heightCm)
            _userProfile.value = updated
            refreshAnalytics()
            _statusMessage.value = "Logged height: ${heightCm} cm"
        }
    }

    fun deleteHeight(id: Long) {
        viewModelScope.launch {
            repository.deleteHeightRecord(id)
            refreshAnalytics()
        }
    }

    fun updateHabit(sleepHours: Float, waterGlasses: Int) {
        viewModelScope.launch {
            repository.saveDailyHabit(
                DailyHabitEntity(
                    dateString = repository.getTodayDateString(),
                    sleepHours = sleepHours,
                    waterGlasses = waterGlasses
                )
            )
            refreshAnalytics()
        }
    }

    fun updateUserProfile(profile: UserProfile) {
        repository.saveUserProfile(profile)
        _userProfile.value = profile
        refreshAnalytics()
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    fun sendTestReminder(typeIndex: Int = 0) {
        val reminder = NotificationHelper.PRESET_REMINDERS.getOrElse(typeIndex) { NotificationHelper.PRESET_REMINDERS[0] }
        NotificationHelper.sendGrowthReminderNotification(
            context = getApplication(),
            title = reminder.first,
            message = reminder.second
        )
        _statusMessage.value = "Notification sent: ${reminder.first}"
    }
}

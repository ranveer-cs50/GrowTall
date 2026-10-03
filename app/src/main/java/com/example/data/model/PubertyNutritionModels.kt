package com.example.data.model

enum class BiologicalSex {
    MALE,
    FEMALE
}

enum class GrowthVerdict(val label: String, val description: String) {
    SUPERFOOD("Height Superfood", "Optimal for puberty bone elongation & HGH release"),
    GROWTH_BOOSTER("Growth Booster", "Rich in protein & key minerals for bone density"),
    NEUTRAL("Moderate / Neutral", "Balanced food with minor impact on growth spurts"),
    GROWTH_INHIBITOR("Growth Caution", "High sugar or sodium may inhibit HGH or leach calcium")
}

data class UserProfile(
    val name: String = "Teen Explorer",
    val age: Int = 14, // 10 to 17
    val biologicalSex: BiologicalSex = BiologicalSex.MALE,
    val currentHeightCm: Float = 162.0f,
    val targetHeightCm: Float = 180.0f,
    val currentWeightKg: Float = 52.0f,
    val targetProteinG: Int = 75,
    val targetCalciumMg: Int = 1300,
    val maxSugarG: Int = 25,
    val maxSodiumMg: Int = 2200,
    val targetSleepHours: Float = 9.0f,
    val bedtimeReminderHour: Int = 21,
    val bedtimeReminderMinute: Int = 30,
    val notificationsEnabled: Boolean = true
) {
    val pubertyGrowthStage: String
        get() = when (biologicalSex) {
            BiologicalSex.MALE -> when {
                age in 10..11 -> "Early Puberty (Pre-Spurt Foundation)"
                age in 12..15 -> "Peak Height Velocity (Maximum Growth Spurt)"
                else -> "Late Adolescence (Epiphyseal Consolidation)"
            }
            BiologicalSex.FEMALE -> when {
                age in 10..12 -> "Peak Height Velocity (Maximum Growth Spurt)"
                age in 13..14 -> "Post-Menarche Deceleration Window"
                else -> "Late Adolescence (Bone Mineralization Peak)"
            }
        }

    val recommendedDailyProteinG: Int
        get() {
            // Pubertal adolescents require 1.3 - 1.6 g/kg body weight during peak growth spurts
            val factor = if (age in 12..15) 1.5f else 1.3f
            return (currentWeightKg * factor).toInt().coerceIn(55, 110)
        }
}

data class FoodScanResult(
    val foodName: String,
    val servingSize: String,
    val proteinG: Float,
    val calciumMg: Float,
    val sugarG: Float,
    val sodiumMg: Float,
    val caloriesKcal: Int,
    val heightGrowthScore: Int, // 1 - 10
    val growthVerdict: GrowthVerdict,
    val keyGrowthNutrients: List<String>,
    val pubertyImpactExplanation: String,
    val redFlags: List<String>,
    val healthyAlternatives: List<MealAlternative>
)

data class MealAlternative(
    val title: String,
    val subtitle: String,
    val proteinG: Float,
    val calciumMg: Float,
    val growthBenefit: String,
    val whyBetter: String,
    val ingredients: List<String> = emptyList()
)

data class HeightRecord(
    val id: Long = 0,
    val dateString: String,
    val heightCm: Float,
    val timestamp: Long = System.currentTimeMillis(),
    val note: String = ""
)

data class MealItem(
    val id: Long = 0,
    val dateString: String,
    val mealType: String, // Breakfast, Lunch, Dinner, Snack
    val foodName: String,
    val proteinG: Float,
    val calciumMg: Float,
    val sugarG: Float,
    val sodiumMg: Float,
    val caloriesKcal: Int,
    val growthScore: Int,
    val timestamp: Long = System.currentTimeMillis()
)

data class GrowthAnalytics(
    val currentHeightCm: Float,
    val targetHeightCm: Float,
    val estimatedAdultHeightCm: Float,
    val annualGrowthVelocityCm: Float,
    val cdcPercentile: Int,
    val weeklyAvgProteinG: Float,
    val weeklyAvgCalciumMg: Float,
    val weeklyAvgSugarG: Float,
    val weeklyAvgSodiumMg: Float,
    val weeklyAvgSleepHours: Float,
    val hghOptimizationScore: Int // 0 - 100%
)

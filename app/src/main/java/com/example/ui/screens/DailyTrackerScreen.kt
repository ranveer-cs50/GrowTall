package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.MealItem
import com.example.ui.components.EditorialTagHeader
import com.example.ui.theme.BorderSubtleColor
import com.example.ui.theme.CautionAmber
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueLight
import com.example.ui.theme.GrowthGreen
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkMuted
import com.example.ui.theme.InkSecondary
import com.example.ui.theme.WarmPaperBackground
import com.example.ui.theme.WarmPaperSurface
import com.example.ui.theme.WarningRed
import com.example.ui.viewmodel.GrowthNutritionViewModel

@Composable
fun DailyTrackerScreen(
    viewModel: GrowthNutritionViewModel
) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val todayMeals by viewModel.todayMeals.collectAsStateWithLifecycle()
    val dailyHabit by viewModel.dailyHabit.collectAsStateWithLifecycle()

    var showAddMealDialog by remember { mutableStateOf(false) }

    // Aggregate today's macros
    val totalProtein = todayMeals.sumOf { it.proteinG.toDouble() }.toFloat()
    val totalCalcium = todayMeals.sumOf { it.calciumMg.toDouble() }.toFloat()
    val totalSugar = todayMeals.sumOf { it.sugarG.toDouble() }.toFloat()
    val totalSodium = todayMeals.sumOf { it.sodiumMg.toDouble() }.toFloat()
    val totalCalories = todayMeals.sumOf { it.caloriesKcal }

    val currentSleep = dailyHabit?.sleepHours ?: 8.5f
    val currentWater = dailyHabit?.waterGlasses ?: 6

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Card with Banner
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderSubtleColor),
                shadowElevation = 2.dp
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_growth_banner_1791036143715),
                        contentDescription = "Teen Puberty Growth",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.45f))
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "${userProfile.name}'s Growth Hub",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = "${userProfile.age} yrs • ${userProfile.biologicalSex.name} • ${userProfile.pubertyGrowthStage}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        // Daily Protein Target Ring & Calcium Hero Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = WarmPaperSurface,
                border = BorderStroke(1.dp, BorderSubtleColor),
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            EditorialTagHeader(
                                tag = "DAILY TARGETS",
                                title = "Today's Growth Fuel",
                                subtitle = "$totalCalories kcal logged today"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Dual Metric Gauges: Protein (Key) & Calcium
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Protein Circular Gauge (Electric Blue)
                        val proteinProgress = (totalProtein / userProfile.targetProteinG.coerceAtLeast(1)).coerceIn(0f, 1f)
                        val animatedProteinProgress by animateFloatAsState(targetValue = proteinProgress, label = "protein")

                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                progress = { animatedProteinProgress },
                                modifier = Modifier.size(105.dp),
                                color = ElectricBlue,
                                strokeWidth = 9.dp,
                                trackColor = ElectricBlue.copy(alpha = 0.12f)
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${totalProtein.toInt()}g",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = ElectricBlue
                                )
                                Text(
                                    text = "of ${userProfile.targetProteinG}g",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = InkSecondary
                                )
                                Text(
                                    text = "PROTEIN",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = InkBlack
                                )
                            }
                        }

                        // Calcium Circular Gauge (Growth Green)
                        val calciumProgress = (totalCalcium / userProfile.targetCalciumMg.coerceAtLeast(1)).coerceIn(0f, 1f)
                        val animatedCalciumProgress by animateFloatAsState(targetValue = calciumProgress, label = "calcium")

                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                progress = { animatedCalciumProgress },
                                modifier = Modifier.size(105.dp),
                                color = GrowthGreen,
                                strokeWidth = 9.dp,
                                trackColor = GrowthGreen.copy(alpha = 0.12f)
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${totalCalcium.toInt()}mg",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = GrowthGreen
                                )
                                Text(
                                    text = "of ${userProfile.targetCalciumMg}mg",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = InkSecondary
                                )
                                Text(
                                    text = "CALCIUM",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = InkBlack
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Puberty Spurt Protein Advice
                    val remainingProtein = (userProfile.targetProteinG - totalProtein).coerceAtLeast(0f)
                    Surface(
                        color = WarmPaperBackground,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, BorderSubtleColor)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = ElectricBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (remainingProtein > 0f) {
                                    "${remainingProtein.toInt()}g protein needed today to fuel epiphyseal cartilage expansion!"
                                } else {
                                    "🎉 Daily protein goal crushed! Your bones have the nitrogen amino acids needed to lengthen."
                                },
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = InkBlack
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Log Meal Button (Electric Blue with Arrow)
                    Button(
                        onClick = { showAddMealDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("quick_add_meal_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Log Meal Manually", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        // Red-Flag Watchdogs: High Sugar & Sodium
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = WarmPaperSurface,
                border = BorderStroke(1.dp, BorderSubtleColor),
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    EditorialTagHeader(
                        tag = "GROWTH WATCHDOGS",
                        title = "Sugar & Sodium Limits",
                        subtitle = "Excess sugar shuts off HGH; excess sodium leaches bone calcium"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Added Sugar Watchdog
                    val sugarRatio = (totalSugar / userProfile.maxSugarG).coerceIn(0f, 1f)
                    val sugarColor = if (totalSugar > userProfile.maxSugarG) WarningRed else if (totalSugar > userProfile.maxSugarG * 0.75f) CautionAmber else GrowthGreen

                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Added Sugar: ${totalSugar.toInt()}g / ${userProfile.maxSugarG}g limit", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                            Text(
                                text = if (totalSugar > userProfile.maxSugarG) "⚠️ HGH Inhibited" else "Safe Zone",
                                color = sugarColor,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { sugarRatio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = sugarColor,
                            trackColor = BorderSubtleColor
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Sodium Watchdog
                    val sodiumRatio = (totalSodium / userProfile.maxSodiumMg).coerceIn(0f, 1f)
                    val sodiumColor = if (totalSodium > userProfile.maxSodiumMg) WarningRed else if (totalSodium > userProfile.maxSodiumMg * 0.8f) CautionAmber else GrowthGreen

                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Sodium: ${totalSodium.toInt()}mg / ${userProfile.maxSodiumMg}mg limit", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                            Text(
                                text = if (totalSodium > userProfile.maxSodiumMg) "⚠️ Calcium Leaching" else "Optimal",
                                color = sodiumColor,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { sodiumRatio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = sodiumColor,
                            trackColor = BorderSubtleColor
                        )
                    }
                }
            }
        }

        // Sleep & Spinal Hydration Tracker (Crucial for HGH Peak)
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = WarmPaperSurface,
                border = BorderStroke(1.dp, BorderSubtleColor),
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    EditorialTagHeader(
                        tag = "HGH SECRETION PILLARS",
                        title = "Sleep & Spinal Hydration",
                        subtitle = "75% of puberty height growth occurs during deep slow-wave sleep"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Sleep Counter
                        Surface(
                            modifier = Modifier.weight(1f),
                            color = WarmPaperBackground,
                            border = BorderStroke(1.dp, BorderSubtleColor),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Bedtime, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Sleep", fontWeight = FontWeight.Bold, color = InkBlack)
                                }
                                Text("${currentSleep}h", fontSize = 20.sp, fontWeight = FontWeight.Black)
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(
                                        text = "－",
                                        modifier = Modifier
                                            .clickable { viewModel.updateHabit((currentSleep - 0.5f).coerceAtLeast(4f), currentWater) }
                                            .padding(4.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = "＋",
                                        modifier = Modifier
                                            .clickable { viewModel.updateHabit((currentSleep + 0.5f).coerceAtMost(12f), currentWater) }
                                            .padding(4.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }

                        // Water Glasses Counter
                        Surface(
                            modifier = Modifier.weight(1f),
                            color = WarmPaperBackground,
                            border = BorderStroke(1.dp, BorderSubtleColor),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.LocalDrink, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Spinal Water", fontWeight = FontWeight.Bold, color = InkBlack)
                                }
                                Text("$currentWater glasses", fontSize = 18.sp, fontWeight = FontWeight.Black)
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(
                                        text = "－",
                                        modifier = Modifier
                                            .clickable { viewModel.updateHabit(currentSleep, (currentWater - 1).coerceAtLeast(0)) }
                                            .padding(4.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = "＋",
                                        modifier = Modifier
                                            .clickable { viewModel.updateHabit(currentSleep, (currentWater + 1).coerceAtMost(16)) }
                                            .padding(4.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Today's Meals Section Header
        item {
            EditorialTagHeader(
                tag = "TODAY'S JOURNAL",
                title = "Logged Meals (${todayMeals.size})"
            )
        }

        if (todayMeals.isEmpty()) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = WarmPaperSurface,
                    border = BorderStroke(1.dp, BorderSubtleColor)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = null,
                            tint = InkMuted,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No meals logged for today yet",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = InkSecondary
                        )
                    }
                }
            }
        } else {
            items(todayMeals, key = { it.id }) { meal ->
                MealCardItem(meal = meal, onDelete = { viewModel.deleteMeal(meal.id) })
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Quick Add Custom Meal Dialog
    if (showAddMealDialog) {
        AddMealDialog(
            onDismiss = { showAddMealDialog = false },
            onAdd = { type, name, protein, calcium, sugar, sodium, calories, score ->
                viewModel.logCustomMeal(type, name, protein, calcium, sugar, sodium, calories, score)
                showAddMealDialog = false
            }
        )
    }
}

@Composable
fun MealCardItem(meal: MealItem, onDelete: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = WarmPaperSurface,
        border = BorderStroke(1.dp, BorderSubtleColor),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Meal type indicator pill
            Surface(
                color = when (meal.mealType.lowercase()) {
                    "breakfast" -> CautionAmber
                    "lunch" -> ElectricBlue
                    "dinner" -> Color(0xFF7C3AED)
                    else -> GrowthGreen
                }.copy(alpha = 0.12f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = meal.mealType,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = when (meal.mealType.lowercase()) {
                        "breakfast" -> CautionAmber
                        "lunch" -> ElectricBlue
                        "dinner" -> Color(0xFF7C3AED)
                        else -> GrowthGreen
                    },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = meal.foodName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = InkBlack
                )
                Text(
                    text = "${meal.proteinG}g Protein • ${meal.calciumMg.toInt()}mg Ca • ${meal.caloriesKcal} kcal",
                    style = MaterialTheme.typography.bodySmall,
                    color = InkSecondary
                )
            }

            // Height Growth Score pill
            Surface(
                color = if (meal.growthScore >= 8) GrowthGreen else if (meal.growthScore >= 6) ElectricBlue else WarningRed,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "${meal.growthScore}/10",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Meal",
                    tint = InkMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun AddMealDialog(
    onDismiss: () -> Unit,
    onAdd: (mealType: String, name: String, protein: Float, calcium: Float, sugar: Float, sodium: Float, calories: Int, score: Int) -> Unit
) {
    var mealType by remember { mutableStateOf("Breakfast") }
    var foodName by remember { mutableStateOf("") }
    var proteinText by remember { mutableStateOf("25") }
    var calciumText by remember { mutableStateOf("200") }
    var sugarText by remember { mutableStateOf("5") }
    var sodiumText by remember { mutableStateOf("300") }
    var caloriesText by remember { mutableStateOf("250") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = WarmPaperSurface,
        title = { Text("Log Food / Snack", fontWeight = FontWeight.Bold, color = InkBlack) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // Meal Type selector
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").forEach { type ->
                        val selected = mealType == type
                        Surface(
                            modifier = Modifier.clickable { mealType = type },
                            color = if (selected) ElectricBlue else WarmPaperBackground,
                            border = BorderStroke(1.dp, if (selected) ElectricBlue else BorderSubtleColor),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = type,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                color = if (selected) Color.White else InkBlack,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    label = { Text("Food Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricBlue,
                        unfocusedBorderColor = BorderSubtleColor
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = proteinText,
                        onValueChange = { proteinText = it },
                        label = { Text("Protein (g)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = calciumText,
                        onValueChange = { calciumText = it },
                        label = { Text("Calcium (mg)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = sugarText,
                        onValueChange = { sugarText = it },
                        label = { Text("Sugar (g)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = sodiumText,
                        onValueChange = { sodiumText = it },
                        label = { Text("Sodium (mg)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val p = proteinText.toFloatOrNull() ?: 15f
                    val c = calciumText.toFloatOrNull() ?: 100f
                    val s = sugarText.toFloatOrNull() ?: 5f
                    val sod = sodiumText.toFloatOrNull() ?: 200f
                    val cal = caloriesText.toIntOrNull() ?: 200
                    val score = if (p >= 20 && s <= 10 && sod <= 400) 9 else if (p >= 10 && s <= 15) 7 else 4
                    onAdd(mealType, foodName.ifBlank { "Nutritious Food" }, p, c, s, sod, cal, score)
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Log Food", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = InkSecondary)
            }
        }
    )
}

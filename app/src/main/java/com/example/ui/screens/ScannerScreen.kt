package com.example.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.datasource.GrowthNutritionPresetData
import com.example.data.model.FoodScanResult
import com.example.data.model.GrowthVerdict
import com.example.ui.components.CameraViewfinder
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerScreen(
    viewModel: GrowthNutritionViewModel
) {
    val scanResult by viewModel.scanResult.collectAsStateWithLifecycle()
    val isAnalyzing by viewModel.isAnalyzing.collectAsStateWithLifecycle()
    val capturedBitmap by viewModel.capturedBitmap.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()

    var showCameraView by remember { mutableStateOf(false) }
    var searchTextInput by remember { mutableStateOf("") }
    var showLogMealMenu by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp)
    ) {
        // Scanner Header Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = WarmPaperSurface,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderSubtleColor),
            shadowElevation = 1.dp
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                EditorialTagHeader(
                    tag = "REAL-TIME SCANNER",
                    title = "Detect Protein & Growth Nutrients",
                    subtitle = "Tuned for ${userProfile.age}y ${userProfile.biologicalSex.name.lowercase()} puberty height velocity"
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Electric Blue Open Camera Button (with Arrow icon style like reference)
                    Button(
                        onClick = { showCameraView = !showCameraView },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("toggle_camera_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showCameraView) InkBlack else ElectricBlue
                        )
                    ) {
                        Text(
                            text = if (showCameraView) "Close Camera" else "Open Camera",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Food Search / Name Entry Field
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchTextInput,
                        onValueChange = { searchTextInput = it },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("food_search_input"),
                        placeholder = { Text("Or enter food: e.g. Greek Yogurt, Cola, Eggs", fontSize = 13.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricBlue,
                            unfocusedBorderColor = BorderSubtleColor
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (searchTextInput.isNotBlank()) {
                                viewModel.analyzeFood(null, searchTextInput)
                            }
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .testTag("search_food_button")
                            .background(ElectricBlue, RoundedCornerShape(10.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Food",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        // Live Camera Viewfinder
        if (showCameraView) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                CameraViewfinder(
                    onImageCaptured = { bitmap: Bitmap ->
                        viewModel.analyzeFood(bitmap, null)
                        showCameraView = false
                    },
                    onGalleryImageSelected = { bitmap: Bitmap ->
                        viewModel.analyzeFood(bitmap, null)
                        showCameraView = false
                    }
                )
            }
        }

        // Quick Preset Testing Carousel
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            Text(
                text = "Tap to test growth superfoods & stunting foods:",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = InkMuted,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GrowthNutritionPresetData.VERIFIED_FOOD_DATABASE.forEach { preset ->
                    val isSelected = scanResult?.foodName == preset.foodName
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectPresetFood(preset) },
                        label = {
                            Text(
                                text = preset.foodName.split(" ").take(3).joinToString(" "),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .background(
                                        when (preset.growthVerdict) {
                                            GrowthVerdict.SUPERFOOD -> GrowthGreen
                                            GrowthVerdict.GROWTH_BOOSTER -> ElectricBlue
                                            GrowthVerdict.GROWTH_INHIBITOR -> WarningRed
                                            else -> CautionAmber
                                        },
                                        CircleShape
                                    )
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricBlueLight,
                            selectedLabelColor = ElectricBlue
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = if (isSelected) ElectricBlue else BorderSubtleColor,
                            selectedBorderColor = ElectricBlue,
                            enabled = true,
                            selected = isSelected
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Analysis Loading Indicator
        if (isAnalyzing) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = ElectricBlue)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Analyzing puberty nutrition & growth plate impact...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ElectricBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        } else if (scanResult != null) {
            val result = scanResult!!
            ScanResultDetailCard(
                result = result,
                capturedBitmap = capturedBitmap,
                userProteinTarget = userProfile.targetProteinG,
                onLogMealClick = { showLogMealMenu = true }
            )

            // Log Meal Popup Menu
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                DropdownMenu(
                    expanded = showLogMealMenu,
                    onDismissRequest = { showLogMealMenu = false },
                    modifier = Modifier.background(WarmPaperSurface)
                ) {
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").forEach { mealType ->
                        DropdownMenuItem(
                            text = { Text("Log to $mealType", fontWeight = FontWeight.SemiBold) },
                            onClick = {
                                viewModel.logCurrentScanMeal(mealType)
                                showLogMealMenu = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.DinnerDining, contentDescription = null, tint = ElectricBlue)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScanResultDetailCard(
    result: FoodScanResult,
    capturedBitmap: Bitmap?,
    userProteinTarget: Int,
    onLogMealClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("scan_result_card"),
        shape = RoundedCornerShape(16.dp),
        color = WarmPaperSurface,
        border = BorderStroke(1.dp, BorderSubtleColor),
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Captured photo thumbnail if available
            if (capturedBitmap != null) {
                Image(
                    bitmap = capturedBitmap.asImageBitmap(),
                    contentDescription = "Captured Food",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, BorderSubtleColor, RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Food Name & Serving Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = result.foodName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = InkBlack
                    )
                    Text(
                        text = "${result.servingSize} • ${result.caloriesKcal} kcal",
                        style = MaterialTheme.typography.bodyMedium,
                        color = InkSecondary
                    )
                }

                // Growth Score Badge (1 - 10)
                Surface(
                    color = when {
                        result.heightGrowthScore >= 8 -> GrowthGreen
                        result.heightGrowthScore >= 6 -> ElectricBlue
                        else -> WarningRed
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${result.heightGrowthScore}/10",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Height Score",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Growth Verdict Badge
            val verdictColor = when (result.growthVerdict) {
                GrowthVerdict.SUPERFOOD -> GrowthGreen
                GrowthVerdict.GROWTH_BOOSTER -> ElectricBlue
                GrowthVerdict.GROWTH_INHIBITOR -> WarningRed
                GrowthVerdict.NEUTRAL -> CautionAmber
            }
            Surface(
                color = verdictColor.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, verdictColor.copy(alpha = 0.4f)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (result.growthVerdict == GrowthVerdict.GROWTH_INHIBITOR) Icons.Default.Warning else Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = verdictColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${result.growthVerdict.label}: ${result.growthVerdict.description}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = verdictColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nutrient Breakdown Grid
            Text(
                text = "Key Puberty Growth Nutrients",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = InkBlack
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Protein box (Hero in Electric Blue)
                NutrientBox(
                    modifier = Modifier.weight(1f),
                    label = "PROTEIN",
                    value = "${result.proteinG}g",
                    subtext = "${((result.proteinG / userProteinTarget) * 100).toInt()}% goal",
                    highlightColor = ElectricBlue
                )

                // Calcium box
                NutrientBox(
                    modifier = Modifier.weight(1f),
                    label = "CALCIUM",
                    value = "${result.calciumMg.toInt()}mg",
                    subtext = "${((result.calciumMg / 1300f) * 100).toInt()}% target",
                    highlightColor = GrowthGreen
                )

                // Sugar watchdog
                NutrientBox(
                    modifier = Modifier.weight(1f),
                    label = "SUGAR",
                    value = "${result.sugarG}g",
                    subtext = if (result.sugarG > 15) "⚠️ HGH Risk" else "Optimal",
                    highlightColor = if (result.sugarG > 15) WarningRed else GrowthGreen
                )

                // Sodium watchdog
                NutrientBox(
                    modifier = Modifier.weight(1f),
                    label = "SODIUM",
                    value = "${result.sodiumMg.toInt()}mg",
                    subtext = if (result.sodiumMg > 600) "⚠️ High Salt" else "Balanced",
                    highlightColor = if (result.sodiumMg > 600) WarningRed else GrowthGreen
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Puberty Growth Medical Breakdown
            Surface(
                color = WarmPaperBackground,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderSubtleColor)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = ElectricBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Puberty Growth Plate & HGH Action:",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = InkBlack
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = result.pubertyImpactExplanation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = InkSecondary,
                        lineHeight = 20.sp
                    )
                }
            }

            // Red Flags / Caution warnings (Sodium & Sugar)
            if (result.redFlags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))
                Surface(
                    color = WarningRed.copy(alpha = 0.06f),
                    border = BorderStroke(1.dp, WarningRed.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = WarningRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Growth Inhibitor Alert (Sugar & Sodium)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = WarningRed
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        result.redFlags.forEach { flag ->
                            Text(
                                text = "• $flag",
                                style = MaterialTheme.typography.bodySmall,
                                color = InkBlack,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            // Nutritionist-Verified Healthy Meal Alternatives
            if (result.healthyAlternatives.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                EditorialTagHeader(
                    tag = "NUTRITIONIST VERIFIED",
                    title = "Height Boost Alternatives"
                )
                Spacer(modifier = Modifier.height(8.dp))

                result.healthyAlternatives.forEach { alt ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        color = GrowthGreen.copy(alpha = 0.06f),
                        border = BorderStroke(1.dp, GrowthGreen.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = alt.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = GrowthGreen,
                                    modifier = Modifier.weight(1f)
                                )
                                Surface(
                                    color = GrowthGreen,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "+${alt.proteinG}g Protein",
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            Text(
                                text = alt.subtitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = InkSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "✓ ${alt.growthBenefit}",
                                style = MaterialTheme.typography.bodySmall,
                                color = InkBlack,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action: Log to Daily Tracker (Electric Blue Button with Arrow)
            Button(
                onClick = onLogMealClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("log_scanned_meal_button"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Log to Today's Meals",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun NutrientBox(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    subtext: String,
    highlightColor: Color
) {
    Surface(
        modifier = modifier,
        color = WarmPaperBackground,
        border = BorderStroke(1.dp, BorderSubtleColor),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontSize = 9.sp,
                fontWeight = FontWeight.ExtraBold,
                color = highlightColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = InkBlack
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtext,
                fontSize = 9.sp,
                color = InkSecondary,
                maxLines = 1
            )
        }
    }
}

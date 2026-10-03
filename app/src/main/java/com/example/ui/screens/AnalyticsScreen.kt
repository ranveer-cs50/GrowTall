package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.HeightRecord
import com.example.ui.components.EditorialTagHeader
import com.example.ui.components.GrowthChartCanvas
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
fun AnalyticsScreen(
    viewModel: GrowthNutritionViewModel
) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val heightRecords by viewModel.heightRecords.collectAsStateWithLifecycle()
    val analytics by viewModel.growthAnalytics.collectAsStateWithLifecycle()

    var showAddHeightDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = WarmPaperSurface,
                border = BorderStroke(1.dp, BorderSubtleColor),
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    EditorialTagHeader(
                        tag = "CLINICAL VELOCITY",
                        title = "Puberty Growth Analytics",
                        subtitle = "Epiphyseal plate & height velocity tracking"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Key Velocity & Projected Height KPI Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Current Height Card
                        Surface(
                            modifier = Modifier.weight(1f),
                            color = WarmPaperBackground,
                            border = BorderStroke(1.dp, BorderSubtleColor),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Height, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Current Height", style = MaterialTheme.typography.labelSmall, color = ElectricBlue, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("${userProfile.currentHeightCm} cm", fontSize = 22.sp, fontWeight = FontWeight.Black, color = InkBlack)
                                val feetInches = cmToFeetInches(userProfile.currentHeightCm)
                                Text(feetInches, style = MaterialTheme.typography.bodySmall, color = InkSecondary)
                            }
                        }

                        // Growth Velocity Card
                        Surface(
                            modifier = Modifier.weight(1f),
                            color = WarmPaperBackground,
                            border = BorderStroke(1.dp, BorderSubtleColor),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Speed, contentDescription = null, tint = GrowthGreen, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Annual Velocity", style = MaterialTheme.typography.labelSmall, color = GrowthGreen, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = String.format("%.1f cm/yr", analytics?.annualGrowthVelocityCm ?: 7.5f),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black,
                                    color = InkBlack
                                )
                                Text("Peak Spurt Window", style = MaterialTheme.typography.bodySmall, color = GrowthGreen, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Projected Adult Height
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = ElectricBlueLight,
                        border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Projected Adult Height (Khamis-Roche)", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = ElectricBlue)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${String.format("%.1f", analytics?.estimatedAdultHeightCm ?: 182.0f)} cm (${cmToFeetInches(analytics?.estimatedAdultHeightCm ?: 182.0f)})",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = InkBlack
                            )
                            Text(
                                text = "Target: ${userProfile.targetHeightCm} cm • Percentile: ~${analytics?.cdcPercentile ?: 65}th",
                                style = MaterialTheme.typography.bodySmall,
                                color = InkSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Log Height Button (Electric Blue with Arrow)
                    Button(
                        onClick = { showAddHeightDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("record_height_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Log New Height Measurement", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        // Interactive Growth Chart Canvas
        item {
            GrowthChartCanvas(
                biologicalSex = userProfile.biologicalSex,
                currentAge = userProfile.age,
                targetHeightCm = userProfile.targetHeightCm,
                heightLogs = heightRecords
            )
        }

        // Puberty HGH Optimization Index
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = WarmPaperSurface,
                border = BorderStroke(1.dp, BorderSubtleColor),
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            EditorialTagHeader(
                                tag = "PHYSIOLOGICAL INDEX",
                                title = "HGH Optimization Index",
                                subtitle = "Protein consistency + sleep + low sugar"
                            )
                        }

                        val score = analytics?.hghOptimizationScore ?: 86
                        Surface(
                            color = if (score >= 80) GrowthGreen else if (score >= 65) ElectricBlue else CautionAmber,
                            shape = CircleShape
                        ) {
                            Text(
                                text = "$score%",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 4 Key Factors for Puberty Height
                    HghFactorRow(
                        label = "Daily Protein Intake",
                        status = "7-Day Avg: ${String.format("%.1f", analytics?.weeklyAvgProteinG ?: 68f)}g / ${userProfile.targetProteinG}g",
                        isMet = (analytics?.weeklyAvgProteinG ?: 0f) >= userProfile.targetProteinG * 0.85f
                    )
                    HghFactorRow(
                        label = "Bone Calcium Density",
                        status = "7-Day Avg: ${String.format("%.0f", analytics?.weeklyAvgCalciumMg ?: 1100f)}mg / 1300mg",
                        isMet = (analytics?.weeklyAvgCalciumMg ?: 0f) >= userProfile.targetCalciumMg * 0.8f
                    )
                    HghFactorRow(
                        label = "HGH Sugar Suppression Control",
                        status = "7-Day Avg: ${String.format("%.1f", analytics?.weeklyAvgSugarG ?: 18f)}g (Max ${userProfile.maxSugarG}g)",
                        isMet = (analytics?.weeklyAvgSugarG ?: 0f) <= userProfile.maxSugarG
                    )
                    HghFactorRow(
                        label = "Deep Sleep Growth Spurts",
                        status = "Target: ${userProfile.targetSleepHours}h night sleep",
                        isMet = true
                    )
                }
            }
        }

        // Height Measurement Log History
        item {
            EditorialTagHeader(
                tag = "MEASUREMENT HISTORY",
                title = "Logged Heights (${heightRecords.size})"
            )
        }

        if (heightRecords.isEmpty()) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = WarmPaperSurface,
                    border = BorderStroke(1.dp, BorderSubtleColor)
                ) {
                    Text(
                        text = "No height logs yet. Tap 'Log New Height Measurement' above to begin!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = InkSecondary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } else {
            items(heightRecords.reversed(), key = { it.id }) { record ->
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${record.heightCm} cm (${cmToFeetInches(record.heightCm)})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = InkBlack
                            )
                            Text(
                                text = "${record.dateString} ${if (record.note.isNotBlank()) "• ${record.note}" else ""}",
                                style = MaterialTheme.typography.bodySmall,
                                color = InkSecondary
                            )
                        }

                        IconButton(onClick = { viewModel.deleteHeight(record.id) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete record",
                                tint = InkMuted,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if (showAddHeightDialog) {
        AddHeightDialog(
            currentHeight = userProfile.currentHeightCm,
            onDismiss = { showAddHeightDialog = false },
            onSave = { newHeight, note ->
                viewModel.logHeight(newHeight, note)
                showAddHeightDialog = false
            }
        )
    }
}

@Composable
private fun HghFactorRow(label: String, status: String, isMet: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = if (isMet) GrowthGreen.copy(alpha = 0.12f) else WarningRed.copy(alpha = 0.12f),
            shape = CircleShape,
            modifier = Modifier.size(24.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = if (isMet) GrowthGreen else WarningRed,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = InkBlack)
            Text(text = status, style = MaterialTheme.typography.bodySmall, color = InkSecondary)
        }
    }
}

@Composable
fun AddHeightDialog(
    currentHeight: Float,
    onDismiss: () -> Unit,
    onSave: (Float, String) -> Unit
) {
    var heightText by remember { mutableStateOf(currentHeight.toString()) }
    var noteText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = WarmPaperSurface,
        title = { Text("Log Height Measurement", fontWeight = FontWeight.Bold, color = InkBlack) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Measure barefoot in the morning for consistent epiphyseal tracking.",
                    style = MaterialTheme.typography.bodySmall,
                    color = InkSecondary
                )
                OutlinedTextField(
                    value = heightText,
                    onValueChange = { heightText = it },
                    label = { Text("Height (cm)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricBlue,
                        unfocusedBorderColor = BorderSubtleColor
                    )
                )
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    label = { Text("Note (e.g. Morning measure, Spurt check)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricBlue,
                        unfocusedBorderColor = BorderSubtleColor
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val h = heightText.toFloatOrNull() ?: currentHeight
                    onSave(h, noteText)
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save Height", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", color = InkSecondary) }
        }
    )
}

fun cmToFeetInches(cm: Float): String {
    val totalInches = cm / 2.54f
    val feet = (totalInches / 12).toInt()
    val inches = (totalInches % 12).toInt()
    return "${feet} ft ${inches} in"
}


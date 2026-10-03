package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.BiologicalSex
import com.example.data.model.UserProfile
import com.example.ui.components.EditorialTagHeader
import com.example.ui.theme.BorderSubtleColor
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueLight
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkSecondary
import com.example.ui.theme.WarmPaperBackground
import com.example.ui.theme.WarmPaperSurface
import com.example.ui.viewmodel.GrowthNutritionViewModel

@Composable
fun ProfileSettingsScreen(
    viewModel: GrowthNutritionViewModel
) {
    val context = LocalContext.current
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()

    var name by remember(userProfile) { mutableStateOf(userProfile.name) }
    var age by remember(userProfile) { mutableIntStateOf(userProfile.age) }
    var sex by remember(userProfile) { mutableStateOf(userProfile.biologicalSex) }
    var heightCm by remember(userProfile) { mutableFloatStateOf(userProfile.currentHeightCm) }
    var targetHeightCm by remember(userProfile) { mutableFloatStateOf(userProfile.targetHeightCm) }
    var weightKg by remember(userProfile) { mutableFloatStateOf(userProfile.currentWeightKg) }
    var notificationsEnabled by remember(userProfile) { mutableStateOf(userProfile.notificationsEnabled) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            notificationsEnabled = granted
            viewModel.updateUserProfile(userProfile.copy(notificationsEnabled = granted))
        }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Profile Header
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
                        tag = "USER CONFIGURATION",
                        title = "Puberty Growth Profile",
                        subtitle = "Tailored for Ages 10–17 Adolescent Velocity"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name / Nickname") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricBlue,
                            unfocusedBorderColor = BorderSubtleColor
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Biological Sex Selector (Crucial for CDC curve alignment)
                    Text("Biological Sex (Puberty Velocity Curves):", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = InkBlack)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        val isMale = sex == BiologicalSex.MALE
                        FilterChip(
                            selected = isMale,
                            onClick = { sex = BiologicalSex.MALE },
                            label = { Text("Male (Peak: 12-15y)", fontWeight = if (isMale) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ElectricBlueLight,
                                selectedLabelColor = ElectricBlue
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = if (isMale) ElectricBlue else BorderSubtleColor,
                                selectedBorderColor = ElectricBlue,
                                enabled = true,
                                selected = isMale
                            )
                        )
                        val isFemale = sex == BiologicalSex.FEMALE
                        FilterChip(
                            selected = isFemale,
                            onClick = { sex = BiologicalSex.FEMALE },
                            label = { Text("Female (Peak: 10-13y)", fontWeight = if (isFemale) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ElectricBlueLight,
                                selectedLabelColor = ElectricBlue
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = if (isFemale) ElectricBlue else BorderSubtleColor,
                                selectedBorderColor = ElectricBlue,
                                enabled = true,
                                selected = isFemale
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Age Slider (10 to 17)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Age: $age years old", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Black, color = InkBlack)
                        Text(
                            text = if (age in 12..15) "🔥 Peak Spurt Window" else "Steady Growth",
                            style = MaterialTheme.typography.labelMedium,
                            color = ElectricBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Slider(
                        value = age.toFloat(),
                        onValueChange = { age = it.toInt() },
                        valueRange = 10f..17f,
                        steps = 6,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = ElectricBlue,
                            activeTrackColor = ElectricBlue
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Height & Weight inputs
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = heightCm.toString(),
                            onValueChange = { heightCm = it.toFloatOrNull() ?: heightCm },
                            label = { Text("Height (cm)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricBlue,
                                unfocusedBorderColor = BorderSubtleColor
                            )
                        )
                        OutlinedTextField(
                            value = targetHeightCm.toString(),
                            onValueChange = { targetHeightCm = it.toFloatOrNull() ?: targetHeightCm },
                            label = { Text("Target (cm)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricBlue,
                                unfocusedBorderColor = BorderSubtleColor
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = weightKg.toString(),
                        onValueChange = { weightKg = it.toFloatOrNull() ?: weightKg },
                        label = { Text("Weight (kg)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricBlue,
                            unfocusedBorderColor = BorderSubtleColor
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Auto-calculated recommended protein badge
                    val calculatedProtein = (weightKg * if (age in 12..15) 1.5f else 1.3f).toInt().coerceIn(55, 110)
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
                            Icon(Icons.Default.Shield, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Pediatric Recommended Protein: ${calculatedProtein}g / day",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricBlue
                                )
                                Text(
                                    text = "Based on $weightKg kg body weight & active pubertal cartilage expansion.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = InkSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Save Profile Button (Electric Blue with Arrow)
                    Button(
                        onClick = {
                            val updated = userProfile.copy(
                                name = name,
                                age = age,
                                biologicalSex = sex,
                                currentHeightCm = heightCm,
                                targetHeightCm = targetHeightCm,
                                currentWeightKg = weightKg,
                                targetProteinG = calculatedProtein,
                                notificationsEnabled = notificationsEnabled
                            )
                            viewModel.updateUserProfile(updated)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("save_profile_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save Profile & Recalculate Goals", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        // Smart Growth Intake Reminders & Notifications Card
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = ElectricBlue)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Puberty Intake Reminders", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = InkBlack)
                                Text("HGH sleep & pre-bedtime protein alerts", style = MaterialTheme.typography.bodySmall, color = InkSecondary)
                            }
                        }

                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { checked ->
                                if (checked && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val status = ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                                    if (status != PackageManager.PERMISSION_GRANTED) {
                                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                    } else {
                                        notificationsEnabled = true
                                        viewModel.updateUserProfile(userProfile.copy(notificationsEnabled = true))
                                    }
                                } else {
                                    notificationsEnabled = checked
                                    viewModel.updateUserProfile(userProfile.copy(notificationsEnabled = checked))
                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = ElectricBlue
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text("Test Notification Triggers:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = InkBlack)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.sendTestReminder(0) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, BorderSubtleColor)
                        ) {
                            Text("🌙 Bedtime Protein", fontSize = 11.sp, color = InkBlack, fontWeight = FontWeight.SemiBold)
                        }

                        OutlinedButton(
                            onClick = { viewModel.sendTestReminder(2) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, BorderSubtleColor)
                        ) {
                            Text("🛌 HGH Sleep Alert", fontSize = 11.sp, color = InkBlack, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { viewModel.sendTestReminder(1) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, BorderSubtleColor)
                    ) {
                        Text("⚡ Afternoon Growth Spurt Fuel Reminder", color = InkBlack, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.datasource.GrowthNutritionPresetData
import com.example.data.model.MealAlternative
import com.example.ui.components.EditorialTagHeader
import com.example.ui.theme.BorderSubtleColor
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
fun RecipesVaultScreen(
    viewModel: GrowthNutritionViewModel
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Height Superfoods", "Nocturnal HGH", "Smart Swaps")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Card
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
                        tag = "NUTRITIONIST VAULT",
                        title = "Healthy Meal Alternatives",
                        subtitle = "Recipes & swaps engineered to maximize adolescent height growth"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Categories Filter Chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSelected = selectedCategory == cat
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategory = cat },
                                label = { Text(cat, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
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
            }
        }

        // Smart Swaps Comparison Section (Height Stunting vs Height Boosting)
        if (selectedCategory == "All" || selectedCategory == "Smart Swaps") {
            item {
                EditorialTagHeader(
                    tag = "HEIGHT STUNTING VS BOOSTING",
                    title = "Clinical Swaps Guide"
                )
            }

            item {
                SmartSwapCard(
                    stuntingFood = "Cola & Soda Cans",
                    stuntingReason = "42g sugar spikes insulin and shuts down HGH; phosphoric acid leaches bone calcium.",
                    boostingFood = "Fortified Cocoa Milk & Edamame",
                    boostingBenefit = "22g protein, 380mg calcium, zero HGH suppression, rich in zinc.",
                    onLogClick = {
                        viewModel.logCustomMeal("Snack", "Fortified Cocoa Milk & Edamame", 22f, 380f, 6f, 180f, 240, 10)
                    }
                )
            }

            item {
                SmartSwapCard(
                    stuntingFood = "Instant Ramen Noodles",
                    stuntingReason = "1680mg sodium forces calcium out through urine; fried palm oil inflames growth plates.",
                    boostingFood = "Bone Broth Chicken & Egg Noodle Bowl",
                    boostingBenefit = "32g high-protein, natural collagen matrix, 60% less sodium.",
                    onLogClick = {
                        viewModel.logCustomMeal("Lunch", "Bone Broth Chicken & Egg Noodle Bowl", 32f, 190f, 2f, 480f, 360, 9)
                    }
                )
            }

            item {
                SmartSwapCard(
                    stuntingFood = "Sugary Glazed Doughnuts",
                    stuntingReason = "58g sugar shuts off natural growth hormone for 4 hours; high trans fats.",
                    boostingFood = "Peanut Butter Banana Oat Protein Waffle",
                    boostingBenefit = "24g sustained protein, rich in arginine and potassium to stimulate pituitary gland.",
                    onLogClick = {
                        viewModel.logCustomMeal("Breakfast", "Peanut Butter Banana Oat Protein Waffle", 24f, 220f, 8f, 210f, 320, 9)
                    }
                )
            }
        }

        // Nutritionist Superfood Recipes List
        if (selectedCategory != "Smart Swaps") {
            item {
                EditorialTagHeader(
                    tag = "RECIPE VAULT",
                    title = "Verified Growth Superfood Recipes"
                )
            }

            val filteredRecipes = if (selectedCategory == "Nocturnal HGH") {
                GrowthNutritionPresetData.NUTRITIONIST_RECIPES.filter { it.title.contains("Nocturnal") || it.subtitle.contains("Bedtime") }
            } else {
                GrowthNutritionPresetData.NUTRITIONIST_RECIPES
            }

            items(filteredRecipes) { recipe ->
                RecipeExpandableCard(
                    recipe = recipe,
                    onLogMeal = {
                        val mealType = if (recipe.subtitle.contains("Breakfast")) "Breakfast"
                        else if (recipe.subtitle.contains("Pre-Bedtime") || recipe.subtitle.contains("Snack")) "Snack"
                        else if (recipe.subtitle.contains("Lunch")) "Lunch" else "Dinner"

                        viewModel.logCustomMeal(
                            mealType = mealType,
                            name = recipe.title,
                            proteinG = recipe.proteinG,
                            calciumMg = recipe.calciumMg,
                            sugarG = 4f,
                            sodiumMg = 260f,
                            caloriesKcal = 340,
                            growthScore = 10
                        )
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SmartSwapCard(
    stuntingFood: String,
    stuntingReason: String,
    boostingFood: String,
    boostingBenefit: String,
    onLogClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = WarmPaperSurface,
        border = BorderStroke(1.dp, BorderSubtleColor),
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Bad Food Box
            Surface(
                color = WarningRed.copy(alpha = 0.06f),
                border = BorderStroke(1.dp, WarningRed.copy(alpha = 0.25f)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = WarningRed, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "AVOID: $stuntingFood",
                            fontWeight = FontWeight.Bold,
                            color = WarningRed,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(text = stuntingReason, style = MaterialTheme.typography.bodySmall, color = InkBlack)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Good Food Box
            Surface(
                color = GrowthGreen.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, GrowthGreen.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = GrowthGreen, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "SWAP TO: $boostingFood",
                            fontWeight = FontWeight.Bold,
                            color = GrowthGreen,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(text = boostingBenefit, style = MaterialTheme.typography.bodySmall, color = InkBlack)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Electric Blue Swap button with Arrow
            Button(
                onClick = onLogClick,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Log this Swap", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
            }
        }
    }
}

@Composable
fun RecipeExpandableCard(
    recipe: MealAlternative,
    onLogMeal: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(14.dp),
        color = WarmPaperSurface,
        border = BorderStroke(1.dp, BorderSubtleColor),
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = InkBlack
                    )
                    Text(
                        text = recipe.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = InkSecondary
                    )
                }

                Surface(
                    color = ElectricBlueLight,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "${recipe.proteinG.toInt()}g Protein",
                        color = ElectricBlue,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "✓ ${recipe.growthBenefit}",
                style = MaterialTheme.typography.bodySmall,
                color = InkBlack,
                fontWeight = FontWeight.Medium
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    HorizontalDivider(color = BorderSubtleColor)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Why Adolescent Nutritionists Recommend This:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = recipe.whyBetter,
                        style = MaterialTheme.typography.bodySmall,
                        color = InkSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Key Ingredients:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = InkBlack
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    recipe.ingredients.forEach { ing ->
                        Text(
                            text = "• $ing",
                            style = MaterialTheme.typography.bodySmall,
                            color = InkSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onLogMeal,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Log This Recipe to Today's Meals", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = InkMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

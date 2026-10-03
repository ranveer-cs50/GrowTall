package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.gridBackground
import com.example.ui.theme.BorderSubtleColor
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueLight
import com.example.ui.theme.InkBlack
import com.example.ui.theme.WarmPaperBackground
import com.example.ui.viewmodel.GrowthNutritionViewModel

enum class NavigationTab(
    val title: String,
    val icon: ImageVector,
    val testTag: String
) {
    SCANNER("Scanner", Icons.Default.CameraAlt, "nav_scanner"),
    TRACKER("Daily Log", Icons.Default.Today, "nav_tracker"),
    ANALYTICS("Growth", Icons.Default.AutoGraph, "nav_analytics"),
    RECIPES("Alternatives", Icons.Default.RestaurantMenu, "nav_recipes"),
    PROFILE("Profile", Icons.Default.Person, "nav_profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffold(
    viewModel: GrowthNutritionViewModel
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val statusMessage by viewModel.statusMessage.collectAsStateWithLifecycle()

    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }

    // Handle back button when not on home tab
    if (selectedTabIndex != 0) {
        BackHandler {
            selectedTabIndex = 0
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .gridBackground(),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (NavigationTab.values()[selectedTabIndex]) {
                            NavigationTab.SCANNER -> "GrowTall • Scanner"
                            NavigationTab.TRACKER -> "Puberty Daily Log"
                            NavigationTab.ANALYTICS -> "Height Spurt Analytics"
                            NavigationTab.RECIPES -> "Growth Alternatives"
                            NavigationTab.PROFILE -> "Teen Profile"
                        },
                        fontWeight = FontWeight.Black,
                        style = MaterialTheme.typography.titleLarge,
                        letterSpacing = (-0.3).sp
                    )
                },
                modifier = Modifier.drawBehind {
                    drawLine(
                        color = BorderSubtleColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WarmPaperBackground.copy(alpha = 0.95f),
                    titleContentColor = InkBlack
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.drawBehind {
                    drawLine(
                        color = BorderSubtleColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                },
                containerColor = WarmPaperBackground.copy(alpha = 0.97f),
                tonalElevation = 0.dp
            ) {
                NavigationTab.values().forEachIndexed { index, tab ->
                    val isSelected = selectedTabIndex == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedTabIndex = index },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                fontSize = 11.sp
                            )
                        },
                        modifier = Modifier.testTag(tab.testTag),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = ElectricBlue,
                            unselectedIconColor = InkBlack.copy(alpha = 0.6f),
                            unselectedTextColor = InkBlack.copy(alpha = 0.6f),
                            indicatorColor = ElectricBlue
                        )
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .gridBackground()
        ) {
            when (NavigationTab.values()[selectedTabIndex]) {
                NavigationTab.SCANNER -> ScannerScreen(viewModel = viewModel)
                NavigationTab.TRACKER -> DailyTrackerScreen(viewModel = viewModel)
                NavigationTab.ANALYTICS -> AnalyticsScreen(viewModel = viewModel)
                NavigationTab.RECIPES -> RecipesVaultScreen(viewModel = viewModel)
                NavigationTab.PROFILE -> ProfileSettingsScreen(viewModel = viewModel)
            }
        }
    }
}

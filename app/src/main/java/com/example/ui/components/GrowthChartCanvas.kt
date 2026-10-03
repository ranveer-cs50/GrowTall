package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.datasource.GrowthNutritionPresetData
import com.example.data.model.BiologicalSex
import com.example.data.model.HeightRecord
import com.example.ui.theme.BorderSubtleColor
import com.example.ui.theme.CautionAmber
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.GrowthGreen
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkSecondary
import com.example.ui.theme.WarmPaperSurface

@Composable
fun GrowthChartCanvas(
    modifier: Modifier = Modifier,
    biologicalSex: BiologicalSex = BiologicalSex.MALE,
    currentAge: Int = 14,
    targetHeightCm: Float = 180f,
    heightLogs: List<HeightRecord> = emptyList()
) {
    val curves = if (biologicalSex == BiologicalSex.MALE) {
        GrowthNutritionPresetData.BOYS_GROWTH_CURVES
    } else {
        GrowthNutritionPresetData.GIRLS_GROWTH_CURVES
    }

    val minAge = 10
    val maxAge = 17
    val minHeight = 125f
    val maxHeight = 195f

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = WarmPaperSurface,
        border = BorderStroke(1.dp, BorderSubtleColor),
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            EditorialTagHeader(
                tag = "CLINICAL GROWTH CURVE",
                title = "CDC Puberty Height Trajectory",
                subtitle = "Plotted against WHO/CDC growth percentiles for ages 10 to 17"
            )

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(Color(0xFFFBF8F1), RoundedCornerShape(12.dp))
                    .border(1.dp, BorderSubtleColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val paddingLeft = 100f
                    val paddingRight = 30f
                    val paddingTop = 25f
                    val paddingBottom = 55f

                    val chartWidth = size.width - paddingLeft - paddingRight
                    val chartHeight = size.height - paddingTop - paddingBottom

                    fun ageToX(age: Float): Float {
                        return paddingLeft + ((age - minAge) / (maxAge - minAge)) * chartWidth
                    }

                    fun heightToY(h: Float): Float {
                        val clamped = h.coerceIn(minHeight, maxHeight)
                        return paddingTop + chartHeight - ((clamped - minHeight) / (maxHeight - minHeight)) * chartHeight
                    }

                    // Grid text paint
                    val gridPaint = android.graphics.Paint().apply {
                        color = android.graphics.Color.parseColor("#71717A")
                        textSize = 24f
                        isAntiAlias = true
                        typeface = android.graphics.Typeface.create(android.graphics.Typeface.MONOSPACE, android.graphics.Typeface.NORMAL)
                    }

                    // Horizontal Grid Lines
                    val heightSteps = listOf(130f, 145f, 160f, 175f, 190f)
                    heightSteps.forEach { h ->
                        val y = heightToY(h)
                        drawLine(
                            color = BorderSubtleColor,
                            start = Offset(paddingLeft, y),
                            end = Offset(size.width - paddingRight, y),
                            strokeWidth = 1.dp.toPx()
                        )
                        drawContext.canvas.nativeCanvas.drawText(
                            "${h.toInt()}cm",
                            10f,
                            y + 8f,
                            gridPaint
                        )
                    }

                    // Vertical Age Lines & Labels
                    for (age in minAge..maxAge) {
                        val x = ageToX(age.toFloat())
                        drawLine(
                            color = BorderSubtleColor.copy(alpha = 0.6f),
                            start = Offset(x, paddingTop),
                            end = Offset(x, size.height - paddingBottom),
                            strokeWidth = 1.dp.toPx()
                        )
                        drawContext.canvas.nativeCanvas.drawText(
                            "${age}y",
                            x - 16f,
                            size.height - 12f,
                            gridPaint
                        )
                    }

                    // Peak Spurt Age Window Shading
                    val spurtStartAge = if (biologicalSex == BiologicalSex.MALE) 12.5f else 11.0f
                    val spurtEndAge = if (biologicalSex == BiologicalSex.MALE) 14.8f else 13.2f
                    val spurtX1 = ageToX(spurtStartAge)
                    val spurtX2 = ageToX(spurtEndAge)
                    drawRect(
                        color = ElectricBlue.copy(alpha = 0.05f),
                        topLeft = Offset(spurtX1, paddingTop),
                        size = androidx.compose.ui.geometry.Size(spurtX2 - spurtX1, chartHeight)
                    )

                    // Plot CDC Curves (P90, P50, P10)
                    val p90Path = Path()
                    val p50Path = Path()
                    val p10Path = Path()

                    var isFirst = true
                    for (age in minAge..maxAge) {
                        val triple = curves[age] ?: continue
                        val x = ageToX(age.toFloat())
                        val y10 = heightToY(triple.first)
                        val y50 = heightToY(triple.second)
                        val y90 = heightToY(triple.third)

                        if (isFirst) {
                            p10Path.moveTo(x, y10)
                            p50Path.moveTo(x, y50)
                            p90Path.moveTo(x, y90)
                            isFirst = false
                        } else {
                            p10Path.lineTo(x, y10)
                            p50Path.lineTo(x, y50)
                            p90Path.lineTo(x, y90)
                        }
                    }

                    // Draw P90 line (Dashed green)
                    drawPath(
                        path = p90Path,
                        color = GrowthGreen.copy(alpha = 0.65f),
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)
                        )
                    )

                    // Draw P50 Median line (Ink line)
                    drawPath(
                        path = p50Path,
                        color = InkBlack.copy(alpha = 0.5f),
                        style = Stroke(width = 2.dp.toPx())
                    )

                    // Draw P10 line (Dashed amber)
                    drawPath(
                        path = p10Path,
                        color = CautionAmber.copy(alpha = 0.65f),
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)
                        )
                    )

                    // Draw Target Height line (Electric Blue dashed)
                    if (targetHeightCm in minHeight..maxHeight) {
                        val targetY = heightToY(targetHeightCm)
                        drawLine(
                            color = ElectricBlue,
                            start = Offset(paddingLeft, targetY),
                            end = Offset(size.width - paddingRight, targetY),
                            strokeWidth = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
                        )
                    }

                    // Plot Logged User Height Points
                    if (heightLogs.isNotEmpty()) {
                        val sortedLogs = heightLogs.sortedBy { it.timestamp }
                        val userPath = Path()

                        val baseTimestamp = sortedLogs.first().timestamp
                        val latestTimestamp = sortedLogs.last().timestamp
                        val totalDuration = (latestTimestamp - baseTimestamp).coerceAtLeast(1)

                        sortedLogs.forEachIndexed { index, record ->
                            val fraction = if (sortedLogs.size == 1) 0.5f else (record.timestamp - baseTimestamp).toFloat() / totalDuration
                            val estimatedAge = (currentAge - 0.5f) + fraction * 0.5f
                            val x = ageToX(estimatedAge.coerceIn(minAge.toFloat(), maxAge.toFloat()))
                            val y = heightToY(record.heightCm)

                            if (index == 0) {
                                userPath.moveTo(x, y)
                            } else {
                                userPath.lineTo(x, y)
                            }

                            // Glowing point in Electric Blue
                            drawCircle(
                                color = ElectricBlue.copy(alpha = 0.25f),
                                radius = 9.dp.toPx(),
                                center = Offset(x, y)
                            )
                            drawCircle(
                                color = Color.White,
                                radius = 6.dp.toPx(),
                                center = Offset(x, y)
                            )
                            drawCircle(
                                color = ElectricBlue,
                                radius = 4.dp.toPx(),
                                center = Offset(x, y)
                            )
                        }

                        // Stroke user path
                        drawPath(
                            path = userPath,
                            color = ElectricBlue,
                            style = Stroke(
                                width = 3.5f.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Legend below chart
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendItem(color = GrowthGreen, label = "P90 (90%)")
                LegendItem(color = InkBlack.copy(alpha = 0.6f), label = "P50 (Median)")
                LegendItem(color = CautionAmber, label = "P10 (10%)")
                LegendItem(color = ElectricBlue, label = "Target: ${targetHeightCm.toInt()}cm")
            }
        }
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = InkSecondary,
            fontWeight = FontWeight.Medium
        )
    }
}

package com.example.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GridLineColor
import com.example.ui.theme.WarmPaperBackground

/**
 * Custom Modifier that draws the warm cream background with a subtle technical grid,
 * matching the user's uploaded aesthetic.
 */
fun Modifier.gridBackground(
    cellSize: Dp = 26.dp,
    backgroundColor: Color = WarmPaperBackground,
    lineColor: Color = GridLineColor,
    lineWidth: Dp = 1.dp
): Modifier = this.drawBehind {
    // Fill background color
    drawRect(color = backgroundColor)

    val stepPx = cellSize.toPx()
    val strokeWidthPx = lineWidth.toPx()

    // Draw vertical grid lines
    var x = 0f
    while (x <= size.width) {
        drawLine(
            color = lineColor,
            start = Offset(x, 0f),
            end = Offset(x, size.height),
            strokeWidth = strokeWidthPx
        )
        x += stepPx
    }

    // Draw horizontal grid lines
    var y = 0f
    while (y <= size.height) {
        drawLine(
            color = lineColor,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = strokeWidthPx
        )
        y += stepPx
    }
}

/**
 * Editorial header in the style of the reference image:
 * e.g. "INVESTOR ───────────────" followed by a bold, punchy title.
 */
@Composable
fun EditorialTagHeader(
    modifier: Modifier = Modifier,
    tag: String,
    title: String? = null,
    subtitle: String? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tag.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 1.dp
            )
        }

        if (title != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (subtitle != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

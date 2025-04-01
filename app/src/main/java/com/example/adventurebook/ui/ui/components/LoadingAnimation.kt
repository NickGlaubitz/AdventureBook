package com.example.adventurebook.ui.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.adventurebook.ui.ui.theme.Purple40

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    baseSize: Dp = 32.dp,
    waveCount: Int = 3
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = modifier.size(baseSize * (waveCount + 1))) {
        // Wellenringe
        repeat(waveCount) { index ->
            val waveDelay = index * 400
            val waveAlpha by infiniteTransition.animateFloat(
                initialValue = 0.8f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, delayMillis = waveDelay, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
            val waveScale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = (waveCount + 1).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, delayMillis = waveDelay, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )

            Canvas(modifier = Modifier
                .align(Alignment.Center)
                .size(baseSize)
                .scale(waveScale)) {
                drawCircle(
                    color = Purple40.copy(alpha = waveAlpha),
                    radius = size.width / 2,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        // Zentraler Kreis
        Canvas(modifier = Modifier
            .align(Alignment.Center)
            .size(baseSize)
            .scale(pulseScale)) {
            drawCircle(
                color = Purple40,
                radius = size.width / 2
            )
        }
    }
}

@Preview
@Composable
private fun LoadingAnimationPrev() {
    LoadingAnimation()
}
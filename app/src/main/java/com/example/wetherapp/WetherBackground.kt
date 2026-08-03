package com.example.wetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset

@Composable
fun WetherBackground(
    wetherCondition: String,
    content: @Composable () -> Unit
) {
    val transition = rememberInfiniteTransition(label = "")
    val animation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 7000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    val backgroundBrush = when (wetherCondition.lowercase()) {

        "sunny" -> Brush.linearGradient(
            colors = listOf(
                Color(0xFFFFA726),
                Color(0xFFFFD54F),
                Color(0xFFFFF8E1)
            ),
            start = Offset(0f, 0f),
            end = Offset(
                x = 1000f * animation,
                y = 1800f
            )
        )

        "clear" -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFF0F172A),
                Color(0xFF1E3A8A),
                Color(0xFF3B82F6)
            )
        )

        "cloudy" -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFF546E7A),
                Color(0xFF90A4AE),
                Color(0xFFCFD8DC)
            )
        )

        else -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFF0F172A),
                Color(0xFF1E3A8A),
                Color(0xFF0F172A)
            )
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(backgroundBrush)
    ) {
        CloudEffect()
RainEffect()
        content()
    }
}
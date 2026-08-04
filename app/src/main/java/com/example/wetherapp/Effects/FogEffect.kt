package com.example.wetherapp.Effects



import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun FogEffect() {

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Transparent,
                    Color(0x55FFFFFF),
                    Color(0xAAFFFFFF)
                ),
                startY = size.height * 0.65f,
                endY = size.height
            ),
            topLeft = Offset.Zero,
            size = size
        )

    }
}
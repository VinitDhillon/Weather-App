package com.example.wetherapp

import androidx.compose.runtime.*
import androidx.compose.runtime.withFrameNanos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun CloudEffect() {
    var cloudOffset by remember {
        mutableFloatStateOf(0f)
    }
    LaunchedEffect(Unit) {

        while (true) {

            withFrameNanos {

                cloudOffset += 0.4f

                if (cloudOffset > 1200f) {
                    cloudOffset = -1200f
                }

            }

        }

    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        drawCloud(
            x = 100f + cloudOffset,
            y = 180f,
            scale = 1.2f
        )

        drawCloud(
            x = 550f + cloudOffset,
            y = 250f,
            scale = 0.9f
        )

        drawCloud(
            x = -300f + cloudOffset,
            y = 120f,
            scale = 1.5f
        )

    }

}



private fun DrawScope.drawCloud(
    x: Float,
    y: Float,
    scale: Float = 1f
) {

    val color = Color(0xFF455A64)

    drawCircle(
        color = color,
        radius = 60f * scale,
        center = Offset(x, y)
    )

    drawCircle(
        color = color,
        radius = 75f * scale,
        center = Offset(x + 55f * scale, y - 20f * scale)
    )

    drawCircle(
        color = color,
        radius = 70f * scale,
        center = Offset(x + 120f * scale, y)
    )

    drawCircle(
        color = color,
        radius = 55f * scale,
        center = Offset(x + 180f * scale, y + 15f * scale)
    )

    drawCircle(
        color = color,
        radius = 45f * scale,
        center = Offset(x + 30f * scale, y + 35f * scale)
    )
}
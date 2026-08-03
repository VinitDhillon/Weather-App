package com.example.wetherapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.random.Random
import androidx.compose.runtime.withFrameNanos



private const val DROP_COUNT = 300
private const val MIN_SPEED = 6f
private const val MAX_SPEED = 10f
private const val MIN_LENGTH = 15f
private const val MAX_LENGTH = 35f
private const val WIND = 1.5f

private const val CLOUD_BOTTOM = 220f

@Composable
fun RainEffect() {

    val rainParticles = remember {
        MutableList(DROP_COUNT){

            RainParticle(

                position = Offset(
                    Random.nextFloat() * 1200f,
                    Random.nextFloat() * 1800f + 180f

                ),

                speed = Random.nextFloat() * (MAX_SPEED - MIN_SPEED) + MIN_SPEED,

                length = Random.nextFloat() * (MAX_LENGTH - MIN_LENGTH) + MIN_LENGTH,

                alpha = Random.nextFloat() * 0.5f + 0.3f,

                        wind = Random.nextFloat() * 3f + 2f,

                layer = Random.nextInt(1, 4)

            )

        }

    }
    var frame by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {

        while (true) {

            withFrameNanos {

                frame++

            }

        }

    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        frame

        rainParticles.forEach { particle ->
            particle.position = Offset(
                particle.position.x + (particle.wind * WIND),
                particle.position.y + particle.speed + (particle.layer * 1.5f)
            )


            if (particle.position.y > size.height) {

                particle.position = Offset(
                    Random.nextFloat() * size.width,
                    CLOUD_BOTTOM + Random.nextFloat() * 60f
                )



            }

            val layerAlpha = when (particle.layer) {
                1 -> 0.25f
                2 -> 0.55f
                else -> 0.9f
            }

            val layerWidth = when (particle.layer) {
                1 -> 1f
                2 -> 2f
                else -> 3f
            }

            drawLine(
                color = Color(0xFFB3E5FC).copy(
                    alpha = layerAlpha * particle.alpha
                ),
                start = particle.position,
                end = Offset(
                    particle.position.x + particle.wind * 3,
                    particle.position.y + particle.length
                ),
                strokeWidth = layerWidth * (particle.length / MAX_LENGTH)
            )

        }

    }

}
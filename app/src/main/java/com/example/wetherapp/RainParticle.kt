package com.example.wetherapp

import androidx.compose.ui.geometry.Offset

data class RainParticle(
    var position: Offset,
    var speed: Float,
    var length: Float,
    var alpha: Float,
    var wind: Float,
    var layer: Int
)
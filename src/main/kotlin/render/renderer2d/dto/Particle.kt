package render.renderer2d.dto

import render.common.dto.ColorData

data class Particle(
    val type: Int,
    val size: Float,
    val color: ColorData
)
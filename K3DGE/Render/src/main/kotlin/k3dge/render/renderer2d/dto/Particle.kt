package k3dge.render.renderer2d.dto

import k3dge.render.common.dto.ColorData

data class Particle(val type: Int,
                    val size: Float,
                    val color: ColorData)
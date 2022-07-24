package k3dge.render.renderer2d.dto

import k3dge.render.common.dto.ColorData
import org.joml.Vector3f

data class Circle(val center: Vector3f,
                  val radius: Float,
                  val color: ColorData)
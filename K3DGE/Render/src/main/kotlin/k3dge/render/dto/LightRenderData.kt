package k3dge.render.dto

import org.joml.Vector3f
import org.joml.Vector4f

data class LightRenderData(
    var position: Vector3f,
    var color: Vector4f
)
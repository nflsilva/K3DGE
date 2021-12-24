package k3dge.render.dto

import org.joml.Vector3f
import org.joml.Vector4f
import java.util.*

data class LightRenderData(val uid: UUID,
                           var position: Vector3f,
                           var color: Vector4f
)
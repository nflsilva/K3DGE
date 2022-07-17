package k3dge.render.renderer3d.dto

import org.joml.Vector3f
import org.joml.Vector4f
import java.util.*

data class LightData(val uid: UUID,
                     var position: Vector3f,
                     var color: Vector4f
)
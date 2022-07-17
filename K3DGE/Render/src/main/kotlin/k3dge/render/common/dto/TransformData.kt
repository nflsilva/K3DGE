package k3dge.render.common.dto

import org.joml.Vector3f

data class TransformData(val position: Vector3f,
                         val rotation: Vector3f,
                         val scale: Vector3f)
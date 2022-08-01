package render.renderer3d.dto

import org.joml.Vector3f

data class CameraData(
    var position: Vector3f,
    var forward: Vector3f,
    var up: Vector3f,
    var lookAt: Vector3f
)
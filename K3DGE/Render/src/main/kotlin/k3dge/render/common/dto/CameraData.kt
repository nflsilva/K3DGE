package k3dge.render.common.dto

import org.joml.Vector3f

data class CameraData(var position: Vector3f,
                      var forward: Vector3f,
                      var up: Vector3f,
                      var lookAt: Vector3f
)
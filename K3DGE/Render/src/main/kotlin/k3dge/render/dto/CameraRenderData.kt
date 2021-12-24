package k3dge.render.dto

import org.joml.Vector3f

data class CameraRenderData(var position: Vector3f,
                            var forward: Vector3f,
                            var up: Vector3f,
                            var lookAt: Vector3f
)
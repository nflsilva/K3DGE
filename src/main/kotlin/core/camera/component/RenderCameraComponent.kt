package core.camera.component

import core.common.Component
import core.common.dto.UpdateData
import org.joml.Vector3f
import render.renderer3d.dto.CameraData

class RenderCameraComponent : Component() {
    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData) {
        context.camera?.let { camera ->
            val position = camera.transform.getPos()
            context.graphics.renderCamera(
                CameraData(
                    Vector3f(position.x, position.y, position.z),
                    camera.forward,
                    camera.up,
                    camera.lookAt
                )
            )
        }
    }
}
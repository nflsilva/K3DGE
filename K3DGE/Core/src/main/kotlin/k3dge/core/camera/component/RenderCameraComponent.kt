package k3dge.core.camera.component

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.renderer3d.dto.CameraData
import org.joml.Vector3f

class RenderCameraComponent: Component() {
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
                    camera.lookAt)
            )
        }
    }
}
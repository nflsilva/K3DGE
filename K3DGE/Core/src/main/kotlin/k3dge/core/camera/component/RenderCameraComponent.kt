package k3dge.core.camera.component

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.renderer3d.dto.CameraData

class RenderCameraComponent: Component() {
    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.camera?.let { camera ->
            context.graphics.renderCamera(
                CameraData(camera.transform.position, camera.forward, camera.up, camera.lookAt)
            )
        }
    }
}
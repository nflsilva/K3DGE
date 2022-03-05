package k3dge.core.camera.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.dto.UpdateData
import k3dge.render.renderer3d.dto.CameraRenderData

class RenderCameraComponent: BaseComponent() {
    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.camera?.let { camera ->
            context.graphics.renderCamera(
                CameraRenderData(camera.position, camera.forward, camera.up, camera.lookAt)
            )
        }
    }
}
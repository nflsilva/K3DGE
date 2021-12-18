package k3dge.core.camera.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.UpdateContext
import k3dge.render.dto.CameraRenderData

class RenderCameraComponent: BaseComponent() {
    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateContext) {
        context.camera?.let { camera ->
            context.graphics.renderCamera(
                CameraRenderData(camera.position, camera.forward, camera.up, camera.lookAt)
            )
        }
    }
}
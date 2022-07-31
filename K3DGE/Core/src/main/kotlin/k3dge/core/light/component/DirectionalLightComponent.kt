package k3dge.core.light.component

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.renderer3d.dto.LightData

class DirectionalLightComponent: Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.light?.let { light ->
            context.graphics.renderDirectionalLight(LightData(uid, light.transform.getPos(), light.color))
        }
    }
}
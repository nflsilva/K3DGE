package k3dge.core.light.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.dto.UpdateData
import k3dge.render.dto.LightRenderData

class DirectionalLightComponent: BaseComponent() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.light?.let { light ->
            context.graphics.renderDirectionalLight(LightRenderData(uid, light.position, light.color))
        }
    }
}
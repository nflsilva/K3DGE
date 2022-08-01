package core.light.component

import core.common.Component
import core.common.dto.UpdateData
import render.renderer3d.dto.LightData

class DirectionalLightComponent : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData) {
        context.light?.let { light ->
            context.graphics.renderDirectionalLight(LightData(uid, light.transform.getPos(), light.color))
        }
    }
}
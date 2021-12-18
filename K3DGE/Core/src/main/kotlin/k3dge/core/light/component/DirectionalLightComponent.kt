package k3dge.core.light.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.UpdateContext
import k3dge.core.light.Light
import k3dge.render.RenderEngine
import k3dge.render.dto.LightRenderData
import k3dge.ui.dto.InputStateData
import org.joml.Vector3f
import org.joml.Vector4f
import java.lang.Float
import kotlin.math.abs

class DirectionalLightComponent: BaseComponent() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateContext) {
        context.light?.let { light ->
            context.graphics.renderDirectionalLight(LightRenderData(light.position, light.color))
        }
    }
}
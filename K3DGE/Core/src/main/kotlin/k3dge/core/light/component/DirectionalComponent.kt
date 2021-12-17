package k3dge.core.light.component

import k3dge.core.light.GameLight
import k3dge.render.RenderEngine
import k3dge.render.dto.LightRenderData
import k3dge.ui.dto.InputStateData

class DirectionalComponent: LightComponent() {
    override fun onUpdate(light: GameLight, elapsedTime: Double, input: InputStateData) {
        //TODO: I need to rethink this architecture over...
    }
    override fun onFrame(light: GameLight, renderEngine: RenderEngine) {
        renderEngine.renderDirectionalLight(LightRenderData(light.position, light.color))
    }
}
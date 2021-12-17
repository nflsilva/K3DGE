package k3dge.core.light.component

import k3dge.core.camera.GameCamera
import k3dge.core.common.BaseComponent
import k3dge.core.common.BaseEntity
import k3dge.core.common.ComponentSignal
import k3dge.core.light.GameLight
import k3dge.render.RenderEngine
import k3dge.render.dto.LightRenderData
import k3dge.ui.dto.InputStateData

abstract class LightComponent: BaseComponent() {
    override fun cleanUp() {}
    final override fun onUpdate(entity: BaseEntity, elapsedTime: Double, input: InputStateData) {
        val light = entity as? GameLight ?: return
        onUpdate(light, elapsedTime, input)
    }
    abstract fun onUpdate(light: GameLight, elapsedTime: Double, input: InputStateData)
    override fun onSignal(signal: ComponentSignal) {}
    open fun onFrame(light: GameLight, renderEngine: RenderEngine) {}
}
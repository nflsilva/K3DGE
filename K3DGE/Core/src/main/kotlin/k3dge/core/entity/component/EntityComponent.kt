package k3dge.core.entity.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.BaseEntity
import k3dge.core.common.ComponentSignal
import k3dge.core.entity.GameEntity
import k3dge.render.RenderEngine
import k3dge.ui.dto.InputStateData

abstract class EntityComponent: BaseComponent() {

    override fun cleanUp(){}
    override fun onSignal(signal: ComponentSignal) {}
    final override fun onUpdate(entity: BaseEntity, elapsedTime: Double, input: InputStateData) {
        val gameEntity = entity as? GameEntity ?: return
        onUpdate(gameEntity, elapsedTime, input)
    }
    open fun onUpdate(entity: GameEntity, elapsedTime: Double, input: InputStateData){}
    open fun onFrame(entity: GameEntity, renderEngine: RenderEngine){}
}
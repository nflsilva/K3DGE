package k3dge.core.component

import k3dge.core.entity.GameEntity
import k3dge.render.RenderEngine
import k3dge.ui.InputState

abstract class BaseComponent {
    open fun cleanUp(){}
    open fun onFrame(entity: GameEntity, renderEngine: RenderEngine){}
    open fun onUpdate(entity: GameEntity, elapsedTime: Double, input: InputState){}
}
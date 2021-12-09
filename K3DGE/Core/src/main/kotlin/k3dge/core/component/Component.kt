package k3dge.core.component

import k3dge.core.GameEntity
import k3dge.render.RenderEngine
import k3dge.ui.InputState

interface Component {
    fun cleanUp()
    fun onFrame(entity: GameEntity, renderEngine: RenderEngine)
    fun onUpdate(elapsedTime: Double, input: InputState)
}
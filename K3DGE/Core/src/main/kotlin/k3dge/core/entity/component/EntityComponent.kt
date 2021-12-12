package k3dge.core.entity.component

import k3dge.core.entity.GameEntity
import k3dge.render.RenderEngine
import k3dge.ui.dto.InputStateData

interface EntityComponent {
    fun cleanUp(){}
    fun onFrame(entity: GameEntity, renderEngine: RenderEngine){}
    fun onUpdate(entity: GameEntity, elapsedTime: Double, input: InputStateData){}
}
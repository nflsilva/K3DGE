package k3dge.core.component

import k3dge.core.GameEntity
import k3dge.ui.InputState

class AutoSpinComponent : BaseComponent() {
    override fun onUpdate(entity: GameEntity, elapsedTime: Double, input: InputState) {
        entity.rotation.y += elapsedTime.toFloat()
    }
}
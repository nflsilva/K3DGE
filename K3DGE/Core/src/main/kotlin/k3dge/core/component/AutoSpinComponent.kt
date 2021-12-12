package k3dge.core.component

import k3dge.core.entity.GameEntity
import k3dge.ui.dto.InputStateData

class AutoSpinComponent(private val velocity: Float) : BaseComponent() {
    override fun onUpdate(entity: GameEntity, elapsedTime: Double, input: InputStateData) {
        entity.rotation.y += (velocity * elapsedTime.toFloat())
    }
}
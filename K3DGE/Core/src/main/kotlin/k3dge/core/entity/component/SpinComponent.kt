package k3dge.core.entity.component

import k3dge.core.entity.GameEntity
import k3dge.ui.dto.InputStateData

class SpinComponent(private val velocity: Float) : EntityComponent() {
    override fun onUpdate(entity: GameEntity, elapsedTime: Double, input: InputStateData) {
        entity.rotation.y += (velocity * elapsedTime.toFloat())
    }
}
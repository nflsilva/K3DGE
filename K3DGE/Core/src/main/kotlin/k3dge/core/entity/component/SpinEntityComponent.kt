package k3dge.core.entity.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.UpdateContext
import k3dge.core.entity.Entity
import k3dge.ui.dto.InputStateData

class SpinEntityComponent(private val velocity: Float) : BaseComponent() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateContext) {
        context.entity?.let { entity ->
            entity.rotation.y += (velocity * context.elapsedTime.toFloat())
        }
    }
}
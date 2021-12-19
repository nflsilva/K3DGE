package k3dge.core.entity.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.dto.UpdateData

class SpinEntityComponent(private val velocity: Float) : BaseComponent() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->
            entity.rotation.y += (velocity * context.elapsedTime.toFloat())
        }
    }
}
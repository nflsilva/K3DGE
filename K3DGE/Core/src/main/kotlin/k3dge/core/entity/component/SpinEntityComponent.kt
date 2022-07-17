package k3dge.core.entity.component

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData

class SpinEntityComponent(private val velocity: Float) : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->
            entity.transform.rotation.y += (velocity * context.elapsedTime.toFloat())
        }
    }
}
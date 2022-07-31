package k3dge.core.entity.component

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import org.joml.Vector3f

class SpinEntityComponent(private val velocity: Float) : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->
            val dy = velocity * context.elapsedTime.toFloat()
            entity.transform.rotate(Vector3f(0.0f, dy, 0.0f))
        }
    }
}
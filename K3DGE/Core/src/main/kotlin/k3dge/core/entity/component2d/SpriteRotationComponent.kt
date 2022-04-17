package k3dge.core.entity.component2d

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import org.joml.Vector2f

class SpriteRotationComponent: Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            val r = Vector2f(2f)
            entity.transform.rotation.z += 0.01f
        }
    }
}
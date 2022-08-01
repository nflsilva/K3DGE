package core.entity.component2d

import core.common.Component
import core.common.dto.UpdateData
import org.joml.Vector2f

class SpriteRotationComponent : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->
            val r = Vector2f(2f)
            entity.transform.rotate(0.01f)
        }
    }
}
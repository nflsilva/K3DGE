package core.entity.component

import core.common.Component
import core.common.dto.UpdateData
import org.joml.Vector2f
import ui.dto.InputStateData

class EntityMoveComponent(private val speed: Float = 10F) : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->

            val dd = speed * context.elapsedTime.toFloat()

            if (context.input.isKeyPressed(InputStateData.KEY_A)) {
                entity.transform.translate(Vector2f(-dd, 0f))
            } else if (context.input.isKeyPressed(InputStateData.KEY_D)) {
                entity.transform.translate(Vector2f(dd, 0f))
            }

            if (context.input.isKeyPressed(InputStateData.KEY_W)) {
                entity.transform.translate(Vector2f(0f, dd))
            } else if (context.input.isKeyPressed(InputStateData.KEY_S)) {
                entity.transform.translate(Vector2f(0f, -dd))
            }

        }
    }
}
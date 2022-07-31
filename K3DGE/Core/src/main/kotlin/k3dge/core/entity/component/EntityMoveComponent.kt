package k3dge.core.entity.component

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.ui.dto.InputStateData
import org.joml.Vector2f

class EntityMoveComponent(private val speed: Float = 10F) : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->

            val dd = speed * context.elapsedTime.toFloat()

            if(context.input.isKeyPressed(InputStateData.KEY_A)) {
                entity.transform.translate(Vector2f(-dd, 0f))
            }
            else if(context.input.isKeyPressed(InputStateData.KEY_D)) {
                entity.transform.translate(Vector2f(dd, 0f))
            }

            if(context.input.isKeyPressed(InputStateData.KEY_W)) {
                entity.transform.translate(Vector2f(0f, dd))
            }
            else if(context.input.isKeyPressed(InputStateData.KEY_S)) {
                entity.transform.translate(Vector2f(0f, -dd))
            }

        }
    }
}
package k3dge.core.entity.component

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.ui.dto.InputStateData

class MoveEntityComponent(private val speed: Float = 10F) : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->

            if(context.input.isKeyPressed(InputStateData.KEY_A)) {
                entity.transform.position.x -= speed * context.elapsedTime.toFloat()
            }
            else if(context.input.isKeyPressed(InputStateData.KEY_D)) {
                entity.transform.position.x += speed * context.elapsedTime.toFloat()
            }

            if(context.input.isKeyPressed(InputStateData.KEY_W)) {
                entity.transform.position.y += speed * context.elapsedTime.toFloat()
            }
            else if(context.input.isKeyPressed(InputStateData.KEY_S)) {
                entity.transform.position.y -= speed * context.elapsedTime.toFloat()
            }

        }
    }
}
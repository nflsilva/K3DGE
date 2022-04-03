package k3dge.core.entity.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.dto.UpdateData
import k3dge.ui.dto.InputStateData
import kotlin.math.sin

class MoveEntityComponent() : BaseComponent() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->

            if(context.input.isKeyPressed(InputStateData.KEY_A)) {
                entity.position.x -= 10.0f * context.elapsedTime.toFloat()
            }
            else if(context.input.isKeyPressed(InputStateData.KEY_D)) {
                entity.position.x += 10.0f * context.elapsedTime.toFloat()
            }

            if(context.input.isKeyPressed(InputStateData.KEY_W)) {
                entity.position.y += 10.0f * context.elapsedTime.toFloat()
            }
            else if(context.input.isKeyPressed(InputStateData.KEY_S)) {
                entity.position.y -= 10.0f * context.elapsedTime.toFloat()
            }

        }
    }
}
package core.entity.component2d

import core.common.Component
import core.common.dto.UpdateData
import render.common.model.Texture

class GuiComponent(private val texture: Texture) : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }

    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->
            context.graphics.render2D(texture, entity.transform)
        }
    }

    private fun cleanUp() {
        texture.cleanUp()
    }

}
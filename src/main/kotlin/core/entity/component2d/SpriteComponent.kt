package core.entity.component2d

import core.common.Component
import core.common.dto.UpdateData
import render.renderer2d.dto.Sprite

class SpriteComponent(private val spriteData: Sprite) : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->
            context.graphics.render2D(spriteData, entity.transform)
        }
    }
}
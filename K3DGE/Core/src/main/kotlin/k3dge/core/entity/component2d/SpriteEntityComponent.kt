package k3dge.core.entity.component2d

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.renderer2d.model.Sprite

class SpriteEntityComponent(private val spriteData: Sprite): Component() {
    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            //context.graphics.renderSprite(entity.transform.data, spriteData)
        }
    }
}
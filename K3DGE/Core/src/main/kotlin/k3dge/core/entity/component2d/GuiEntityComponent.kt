package k3dge.core.entity.component2d

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.common.model.Texture

class GuiEntityComponent(private val texture: Texture): Component()  {

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }
    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            context.graphics.render2D(texture, entity.transform.data)
        }
    }
    private fun cleanUp(){
        texture.cleanUp()
    }
}
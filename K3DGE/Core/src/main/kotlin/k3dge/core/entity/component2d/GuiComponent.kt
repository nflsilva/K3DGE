package k3dge.core.entity.component2d

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.common.model.Texture
import k3dge.render.renderer2d.dto.Transform2DData

class GuiComponent(private val texture: Texture): Component()  {

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }
    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            context.graphics.render2D(texture, entity.transform)
        }
    }
    private fun cleanUp(){
        texture.cleanUp()
    }

}
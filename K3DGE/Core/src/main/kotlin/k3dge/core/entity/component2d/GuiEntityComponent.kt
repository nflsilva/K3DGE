package k3dge.core.entity.component2d

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.common.model.Mesh
import k3dge.render.common.shader.Shader
import k3dge.render.common.model.Texture
import k3dge.render.renderer2d.shader.Shader2D

class GuiEntityComponent(private val texture: Texture,
                         private val shader: Shader = Shader2D()): Component()  {

    private val model = Mesh.initQuad()

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }
    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            //context.graphics.renderGui(model, texture, shader, entity.transform.data)
        }
    }
    private fun cleanUp(){
        model.cleanUp()
        texture.cleanUp()
    }
}
package k3dge.core.entity.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.dto.UpdateData
import k3dge.render.renderer3d.dto.EntityRenderData
import k3dge.render.renderer3d.model.MeshGuiModel
import k3dge.render.renderer3d.model.ShaderModel
import k3dge.render.renderer3d.model.TextureModel

class GuiEntityComponent(private val texture: TextureModel,
                         private val shader: ShaderModel
): BaseComponent()  {

    private val model = MeshGuiModel()

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }
    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            context.graphics.renderGui(
                EntityRenderData(
                    uid,
                    model,
                    texture,
                    shader,
                    entity.uid,
                    entity.position,
                    entity.rotation,
                    entity.scale)
            )
        }
    }
    private fun cleanUp(){
        model.cleanUp()
        texture.cleanUp()
    }
}
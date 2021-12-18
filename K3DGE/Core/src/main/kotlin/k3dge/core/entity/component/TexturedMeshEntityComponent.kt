package k3dge.core.entity.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.UpdateContext
import k3dge.core.entity.Entity
import k3dge.render.RenderEngine
import k3dge.render.dto.TexturedMeshRenderData
import k3dge.render.model.MeshModel
import k3dge.render.model.TextureModel

class TexturedMeshEntityComponent(private val mesh: MeshModel,
                                  private val texture: TextureModel) : BaseComponent() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }
    private fun onUpdate(context: UpdateContext){
        context.entity?.let { entity ->
            context.graphics.renderTexturedMesh(
                TexturedMeshRenderData(
                    uid,
                    mesh,
                    texture,
                    entity.shader,
                    entity.uid,
                    entity.position,
                    entity.rotation,
                    entity.scale)
            )
        }
    }
    private fun cleanUp(){
        mesh.cleanUp()
        texture.cleanUp()
    }
}
package k3dge.core.entity.component3d

import k3dge.core.common.BaseComponent
import k3dge.core.common.dto.UpdateData
import k3dge.render.renderer3d.dto.EntityRenderData
import k3dge.render.renderer3d.model.Mesh3DModel
import k3dge.render.common.model.ShaderModel
import k3dge.render.common.model.TextureModel
import k3dge.render.renderer3d.shader.StaticShader

class TexturedMeshEntityComponent(private val mesh: Mesh3DModel,
                                  private val texture: TextureModel,
                                  private val shader: ShaderModel = StaticShader()) : BaseComponent() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }
    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            context.graphics.renderTexturedMesh(
                EntityRenderData(
                    uid,
                    mesh,
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
        mesh.cleanUp()
        texture.cleanUp()
    }
}
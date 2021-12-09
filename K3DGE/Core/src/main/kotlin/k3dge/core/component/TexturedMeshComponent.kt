package k3dge.core.component

import k3dge.core.GameEntity
import k3dge.render.RenderEngine
import k3dge.render.model.MeshModel
import k3dge.render.model.TextureModel
import k3dge.ui.InputState

class TexturedMeshComponent(private val mesh: MeshModel,
                            private val texture: TextureModel) : BaseComponent() {
    override fun cleanUp(){
        mesh.cleanUp()
        texture.cleanUp()
    }
    override fun onFrame(entity: GameEntity, renderEngine: RenderEngine){
        renderEngine.drawTexturedMesh(mesh, texture, entity.shader, entity.position, entity.rotation, entity.scale)
    }
}
package k3dge.core.entity.component

import k3dge.core.entity.GameEntity
import k3dge.render.RenderEngine
import k3dge.render.model.MeshModel
import k3dge.render.model.TextureModel

class TexturedMeshComponent(private val mesh: MeshModel,
                            private val texture: TextureModel) : EntityComponent() {
    override fun cleanUp(){
        mesh.cleanUp()
        texture.cleanUp()
    }
    override fun onFrame(entity: GameEntity, renderEngine: RenderEngine){
        renderEngine.renderTexturedMesh(mesh, texture, entity.shader, entity.position, entity.rotation, entity.scale)
    }
}
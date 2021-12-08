package k3dge.core.component

import k3dge.core.GameEntity
import k3dge.render.RenderEngine
import k3dge.render.model.MeshModel
import k3dge.render.model.TextureModel

class TexturedMeshComponent(
    private val mesh: MeshModel,
    private val texture: TextureModel) {

    fun cleanUp(){
        mesh.cleanUp()
        texture.cleanUp()
    }

    fun onFrame(entity: GameEntity, renderEngine: RenderEngine){
        renderEngine.drawTexturedMesh(mesh, texture, entity.position, entity.rotation, entity.scale)
    }

}
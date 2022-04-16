package k3dge.core.entity.component3d

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.common.model.Mesh
import k3dge.render.common.shader.Shader
import k3dge.render.common.model.Texture
import k3dge.render.renderer3d.shader.Shader3D

class TexturedMeshEntityComponent(private val mesh: Mesh,
                                  private val texture: Texture,
                                  private val shader: Shader = Shader3D()) : Component() {

    constructor(dimensions: Mesh.Dimensions,
                usage: Mesh.Usage,
                meshResource: String,
                textureResource: String, shader: Shader = Shader3D())
            : this(Mesh(dimensions, usage, meshResource), Texture(textureResource), shader)


    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }
    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            context.graphics.render3D(mesh, texture, shader, entity.transform.data)
        }
    }
    private fun cleanUp(){
        mesh.cleanUp()
        texture.cleanUp()
    }
}
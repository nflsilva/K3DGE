package core.entity.component3d

import core.common.Component
import core.common.dto.UpdateData
import render.common.enum.MeshDimensions
import render.common.enum.MeshUsage
import render.common.model.Mesh
import render.common.model.Texture
import render.common.shader.Shader
import render.renderer3d.shader.Shader3D

class TexturedMeshEntityComponent(
    private val mesh: Mesh,
    private val texture: Texture,
    private val shader: Shader = Shader3D()
) : Component() {

    constructor(
        dimensions: MeshDimensions,
        usage: MeshUsage,
        meshResource: String,
        textureResource: String, shader: Shader = Shader3D()
    )
            : this(Mesh(dimensions, usage, meshResource), Texture(textureResource), shader)


    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }

    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->
            context.graphics.render3D(mesh, texture, shader, entity.transform)
        }
    }

    private fun cleanUp() {
        mesh.cleanUp()
        texture.cleanUp()
    }
}
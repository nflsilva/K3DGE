package k3dge.render.dto

import k3dge.render.model.MeshModel
import k3dge.render.model.ShaderModel
import k3dge.render.model.TextureModel
import org.joml.Vector3f

data class TexturedMeshRenderData(
    val mesh: MeshModel,
    val texture: TextureModel,
    val shader: ShaderModel,
    val position: Vector3f,
    val rotation: Vector3f,
    val scale: Vector3f
)
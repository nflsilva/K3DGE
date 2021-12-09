package k3dge.render.dho

import k3dge.render.model.ShaderModel
import org.joml.Matrix4f

data class EntityRenderData(
    val shader: ShaderModel,
    val modelMatrix: Matrix4f
)
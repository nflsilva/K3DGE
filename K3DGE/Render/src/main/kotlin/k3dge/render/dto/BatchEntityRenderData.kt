package k3dge.render.dto

import k3dge.render.renderer3d.dto.ShaderUniformData
import k3dge.render.renderer3d.model.ShaderModel
import org.joml.Matrix4f
import java.util.*

data class BatchEntityRenderData(val id: UUID,
                                 val shader: ShaderModel,
                                 var modelMatrix: Matrix4f) {

    fun prepareShader(uniformData: ShaderUniformData) {
        shader.bind()
        shader.updateUniforms(uniformData)
    }

}


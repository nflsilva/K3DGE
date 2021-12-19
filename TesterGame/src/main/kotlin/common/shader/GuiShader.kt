package common.shader

import k3dge.render.dto.ShaderUniformData
import k3dge.render.model.ShaderModel
import k3dge.tools.ResourceLoader

class GuiShader: ShaderModel(ResourceLoader.loadShaderSourceFromFile("/shader/gui/vertex.glsl")!!,
    ResourceLoader.loadShaderSourceFromFile("/shader/gui/fragment.glsl")!!) {

    init {
        bindAttributes()
        createUniforms()
    }
    override fun bindAttributes() {
        bindAttribute(0, POSITION_ATTRIBUTE);
    }
    override fun createUniforms() {
        addUniform(MODEL_MATRIX_UNIFORM)
    }
    override fun updateUniforms(data: ShaderUniformData) {
        setUniformMatrix4f(MODEL_MATRIX_UNIFORM, data.modelMatrix)
    }

    companion object {
        const val POSITION_ATTRIBUTE = "in_position"
        const val MODEL_MATRIX_UNIFORM = "in_modelMatrix"
    }
}
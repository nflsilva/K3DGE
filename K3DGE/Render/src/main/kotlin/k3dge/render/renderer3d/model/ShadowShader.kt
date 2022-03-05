package k3dge.render.renderer3d.model

import k3dge.render.renderer3d.dto.ShaderUniformData
import k3dge.tools.ResourceLoader

class ShadowShader: ShaderModel(
    ResourceLoader.loadShaderSourceFromFile("/shader/shadow/vertex.glsl")!!,
    ResourceLoader.loadShaderSourceFromFile("/shader/shadow/fragment.glsl")!!) {

    init {
        bindAttributes()
        createUniforms()
    }
    override fun bindAttributes() {
        bindAttribute(0, POSITION_ATTRIBUTE);
    }
    override fun createUniforms() {
        addUniform(MODEL_VIEW_PROJECTION_UNIFORM)
    }
    override fun updateUniforms(data: ShaderUniformData) {
        setUniformMatrix4f(MODEL_VIEW_PROJECTION_UNIFORM, data.modelMatrix)
    }

    companion object {
        const val POSITION_ATTRIBUTE = "in_position"
        const val MODEL_VIEW_PROJECTION_UNIFORM = "in_mvpMatrix"
    }
}
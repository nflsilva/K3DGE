package k3dge.render.renderer3d.shader

import k3dge.render.common.shader.Shader
import k3dge.render.common.dto.ShaderUniformData
import k3dge.tools.ResourceManager

class ShadowShader: Shader(
    ResourceManager.loadShaderSourceFromFile("/shader/3d/shadow/vertex.glsl")!!,
    ResourceManager.loadShaderSourceFromFile("/shader/3d/shadow/fragment.glsl")!!) {

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
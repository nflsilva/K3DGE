package k3dge.render.renderer3d.shader

import k3dge.render.common.dto.ShaderUniformData
import k3dge.render.common.model.Shader
import k3dge.tools.ResourceManager

class StaticShader: Shader(
    ResourceManager.loadShaderSourceFromFile("/shader/static/vertex.glsl")!!,
    ResourceManager.loadShaderSourceFromFile("/shader/static/fragment.glsl")!!) {

    init {
        bindAttributes()
        createUniforms()
    }
    override fun bindAttributes() {
        bindAttribute(0, POSITION_ATTRIBUTE);
        bindAttribute(1, TEXTCOORDS_ATTRIBUTE);
        bindAttribute(2, NORMAL_ATTRIBUTE);
    }
    override fun createUniforms() {
        addUniform(MODEL_MATRIX_UNIFORM)
        addUniform(VIEW_MATRIX_UNIFORM)
        addUniform(PROJECTION_MATRIX_UNIFORM)
        addUniform(LIGHT_DIRECTION_UNIFORM)
        addUniform(LIGHT_COLOR_UNIFORM)
        addUniform(LIGHT_SPACE_MATRIX_UNIFORM)
        addUniform(TEXTURE_0_UNIFORM)
        addUniform(DEPTH_MAP_UNIFORM)
        addUniform(AMBIENT_COEFFICIENT_UNIFORM)
        addUniform(SHADOWS_ENABLED_UNIFORM)
    }
    override fun updateUniforms(data: ShaderUniformData) {
        setUniformMatrix4f(MODEL_MATRIX_UNIFORM, data.modelMatrix)
        setUniformMatrix4f(VIEW_MATRIX_UNIFORM, data.viewMatrix)
        setUniformMatrix4f(PROJECTION_MATRIX_UNIFORM, data.projectionMatrix)
        setUniform3f(LIGHT_DIRECTION_UNIFORM, data.lightDirection)
        setUniform4f(LIGHT_COLOR_UNIFORM, data.lightColor)
        setUniformMatrix4f(LIGHT_SPACE_MATRIX_UNIFORM, data.lightSpaceMatrix)
        setUniformi(TEXTURE_0_UNIFORM, 0)
        setUniformi(DEPTH_MAP_UNIFORM, 1)
        setUniformf(AMBIENT_COEFFICIENT_UNIFORM, data.ambientCoefficient)
        setUniformi(SHADOWS_ENABLED_UNIFORM, if(data.shadowsEnabled) { 1 } else { 0 })
    }

    companion object {
        private const val POSITION_ATTRIBUTE = "in_position"
        private const val TEXTCOORDS_ATTRIBUTE = "in_textureCoords"
        private const val NORMAL_ATTRIBUTE = "in_normal"

        private const val MODEL_MATRIX_UNIFORM = "in_modelMatrix"
        private const val VIEW_MATRIX_UNIFORM = "in_viewMatrix"
        private const val PROJECTION_MATRIX_UNIFORM = "in_projectionMatrix"
        private const val LIGHT_DIRECTION_UNIFORM = "in_lightDirection"
        private const val LIGHT_COLOR_UNIFORM = "in_lightColor"
        private const val LIGHT_SPACE_MATRIX_UNIFORM = "in_lightSpaceMatrix"
        private const val AMBIENT_COEFFICIENT_UNIFORM = "in_ambientCoefficient"
        private const val SHADOWS_ENABLED_UNIFORM = "in_shadowsEnabled"

        private const val TEXTURE_0_UNIFORM = "texture0"
        private const val DEPTH_MAP_UNIFORM = "depthMap"
    }
}
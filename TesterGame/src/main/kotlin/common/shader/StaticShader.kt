package common.shader

import k3dge.render.dto.ShaderUniformData
import k3dge.render.model.ShaderModel
import k3dge.tools.ResourceLoader

class StaticShader():ShaderModel(ResourceLoader.loadShaderSourceFromFile("/shader/static/vertex.glsl")!!,
    ResourceLoader.loadShaderSourceFromFile("/shader/static/fragment.glsl")!!) {

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
    }
    override fun updateUniforms(data: ShaderUniformData) {
        //FIXME: These unwraps will cause trouble for sure...
        setUniformMatrix4f(MODEL_MATRIX_UNIFORM, data.modelMatrix)
        setUniformMatrix4f(VIEW_MATRIX_UNIFORM, data.viewMatrix!!)
        setUniformMatrix4f(PROJECTION_MATRIX_UNIFORM, data.projectionMatrix!!)
        setUniform3f(LIGHT_DIRECTION_UNIFORM, data.lightDirection!!)
        setUniform4f(LIGHT_COLOR_UNIFORM, data.lightColor!!)
    }

    companion object {
        const val POSITION_ATTRIBUTE = "in_position"
        const val TEXTCOORDS_ATTRIBUTE = "in_textureCoords"
        const val NORMAL_ATTRIBUTE = "in_normal"

        const val MODEL_MATRIX_UNIFORM = "in_modelMatrix"
        const val VIEW_MATRIX_UNIFORM = "in_viewMatrix"
        const val PROJECTION_MATRIX_UNIFORM = "in_projectionMatrix"
        const val LIGHT_DIRECTION_UNIFORM = "in_lightDirection"
        const val LIGHT_COLOR_UNIFORM = "in_lightColor"
    }
}
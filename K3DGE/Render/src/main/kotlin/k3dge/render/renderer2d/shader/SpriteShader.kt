package k3dge.render.renderer2d.shader

import k3dge.render.renderer3d.dto.ShaderUniformData
import k3dge.render.common.model.ShaderModel
import k3dge.tools.ResourceLoader

class SpriteShader: ShaderModel(ResourceLoader.loadShaderSourceFromFile("/shader/sprite/vertex.glsl")!!,
    ResourceLoader.loadShaderSourceFromFile("/shader/sprite/fragment.glsl")!!) {

    init {
        bindAttributes()
        createUniforms()
    }
    override fun bindAttributes() {
        bindAttribute(0, POSITION_ATTRIBUTE);
    }
    override fun createUniforms() {
        addUniform(PROJECTION_MATRIX_UNIFORM)
        addUniform(TEXTURE_0_UNIFORM)
    }
    override fun updateUniforms(data: ShaderUniformData) {
        setUniformMatrix4f(PROJECTION_MATRIX_UNIFORM, data.projectionMatrix)
        setUniformi(TEXTURE_0_UNIFORM, 0)
    }

    companion object {
        private const val POSITION_ATTRIBUTE = "in_position"
        private const val PROJECTION_MATRIX_UNIFORM = "in_projectionMatrix"

        private const val TEXTURE_0_UNIFORM = "texture0"
    }
}
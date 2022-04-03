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
        bindAttribute(1, TEXTCOORDS_ATTRIBUTE);
        bindAttribute(2, TEXTINDEX_ATTRIBUTE);
    }
    override fun createUniforms() {
        addUniform(PROJECTION_MATRIX_UNIFORM)
        addUniform(TEXTURE_SLOTS_UNIFORM)
    }
    override fun updateUniforms(data: ShaderUniformData) {
        setUniformMatrix4f(PROJECTION_MATRIX_UNIFORM, data.projectionMatrix)

        val samplers = mutableListOf<Int>()
        for(i in 0 until data.textureSlots){ samplers.add(i) }
        setUniformiv(TEXTURE_SLOTS_UNIFORM, samplers)
    }

    companion object {
        private const val POSITION_ATTRIBUTE = "in_position"
        private const val TEXTCOORDS_ATTRIBUTE = "in_textureCoords"
        private const val TEXTINDEX_ATTRIBUTE = "in_textureIndex"
        private const val PROJECTION_MATRIX_UNIFORM = "in_projectionMatrix"

        private const val TEXTURE_SLOTS_UNIFORM = "in_samplers"
    }
}
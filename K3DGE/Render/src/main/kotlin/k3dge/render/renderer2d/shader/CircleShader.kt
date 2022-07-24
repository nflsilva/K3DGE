package k3dge.render.renderer2d.shader

import k3dge.render.common.dto.ShaderUniformData
import k3dge.render.common.shader.Shader
import org.lwjgl.opengl.GL11.glEnable
import org.lwjgl.opengl.GL20.GL_VERTEX_PROGRAM_POINT_SIZE

class CircleShader: Shader(VERTEX_SHADER, FRAGMENT_SHADER) {

    init {
        bindAttributes()
        createUniforms()
        glEnable(GL_VERTEX_PROGRAM_POINT_SIZE)
    }
    override fun bindAttributes() {
        bindAttribute(0, POSITION_ATTRIBUTE)
        bindAttribute(1, RADIUS_ATTRIBUTE)
        bindAttribute(2, TRANSLATE_ATTRIBUTE)
        bindAttribute(3, ROTATION_ATTRIBUTE)
        bindAttribute(4, SCALE_ATTRIBUTE)
        bindAttribute(5, COLOR_ATTRIBUTE)
    }
    override fun createUniforms() {
        addUniform(PROJECTION_MATRIX_UNIFORM)
    }
    override fun updateUniforms(data: ShaderUniformData) {
        setUniformMatrix4f(PROJECTION_MATRIX_UNIFORM, data.projectionMatrix)
    }

    companion object {
        private const val VERTEX_SHADER = "/shader/2d/circle/vertex.glsl"
        private const val FRAGMENT_SHADER = "/shader/2d/circle/fragment.glsl"

        private const val POSITION_ATTRIBUTE = "in_position"
        private const val RADIUS_ATTRIBUTE = "in_radius"
        private const val TRANSLATE_ATTRIBUTE = "in_translation"
        private const val ROTATION_ATTRIBUTE = "in_rotation"
        private const val SCALE_ATTRIBUTE = "in_scale"
        private const val COLOR_ATTRIBUTE = "in_color"

        private const val PROJECTION_MATRIX_UNIFORM = "in_projectionMatrix"
    }
}
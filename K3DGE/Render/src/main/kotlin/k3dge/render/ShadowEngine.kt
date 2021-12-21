package k3dge.render

import k3dge.render.dto.ShaderUniformData
import k3dge.render.model.ShadowShader
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL30.*
import org.lwjgl.opengl.GL32.GL_TEXTURE_BORDER_COLOR
import org.lwjgl.opengl.GL32.glFramebufferTexture

class ShadowEngine {

    private val depthMap: Int = glGenTextures()
    var lightSpaceMatrix: Matrix4f = Matrix4f().identity()
    private val frameBuffer: Int = glGenFramebuffers()
    private val shadowShader: ShadowShader = ShadowShader()

    init {
        createFrameBuffer()
        createDepthAttachment()
        unbindFramebuffer()
    }
    fun bindFramebuffer(){
        shadowShader.bind()
        glBindFramebuffer(GL_FRAMEBUFFER, depthMap)
        glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT)
    }
    fun unbindFramebuffer(){
        shadowShader.unbind()
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
    }
    fun bindDepthMap(){
        glBindTexture(GL_TEXTURE_2D, depthMap);
    }

    fun prepareLightSpaceMatrix(lightDirection: Vector3f, cameraPosition: Vector3f){
        //TODO: Optimize this light projection.
        val projectionMatrix = Matrix4f().ortho(-15.0F, 15.0F, -15.0F, 15.0F, 1.0F, 300.0F)
        val viewMatrix = Matrix4f().lookAt(
            Vector3f(lightDirection),
            Vector3f(5.0f),
            Vector3f(0.0f, 1.0f, 0.0f))
        lightSpaceMatrix = projectionMatrix.mul(viewMatrix)
    }
    fun updateUniforms(modelMatrix: Matrix4f){
        shadowShader.updateUniforms(ShaderUniformData(Matrix4f(lightSpaceMatrix).mul(modelMatrix)))
    }

    private fun createDepthAttachment(){
        glBindTexture(GL_TEXTURE_2D, depthMap);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL_DEPTH_COMPONENT, GL_FLOAT, null as FloatArray?)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER)
        glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, arrayOf(1.0f, 1.0f, 1.0f, 1.0f).toFloatArray())
        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthMap, 0)
    }
    private fun createFrameBuffer(){
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer)
        glDrawBuffer(GL_NONE)
        glReadBuffer(GL_NONE)
    }

    companion object {
        val SHADOW_WIDTH = 1024 * 2
        val SHADOW_HEIGHT = 1024 * 2
    }
}
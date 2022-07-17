package k3dge.render.renderer3d

import k3dge.render.common.dto.ShaderUniformData
import k3dge.render.renderer3d.shader.ShadowShader
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL30.*
import org.lwjgl.opengl.GL32.GL_TEXTURE_BORDER_COLOR
import org.lwjgl.opengl.GL32.glFramebufferTexture

class RendererShadow(private val shadowWidth: Int,
                     private val shadowHeight: Int) {

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
        glViewport(0, 0, shadowWidth, shadowHeight)
    }
    fun unbindFramebuffer(){
        shadowShader.unbind()
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
    }
    fun bindDepthMap(slot : Int){
        glActiveTexture(GL_TEXTURE0 + slot)
        glBindTexture(GL_TEXTURE_2D, depthMap);
    }

    fun updateLightSpaceMatrix(lightDirection: Vector3f, cameraPosition: Vector3f){
        val scale = cameraPosition.y
        //Log.d("$scale")
        val projectionMatrix = Matrix4f().ortho(
            -3.0F * scale,
            3.0F * scale,
            -3.0F * scale,
            3.0F * scale,
            0.1F,
            5.0F * scale)
        val position = Vector3f(cameraPosition).add(Vector3f(lightDirection).mul(-scale))
        lightSpaceMatrix = Matrix4f(projectionMatrix).lookAt(
            position,
            Vector3f(cameraPosition),
            Vector3f(0.0f, 1.0f, 0.0f))
    }
    fun updateUniforms(modelMatrix: Matrix4f){
        shadowShader.updateUniforms(ShaderUniformData(Matrix4f(lightSpaceMatrix).mul(modelMatrix)))
    }

    private fun createDepthAttachment(){
        glBindTexture(GL_TEXTURE_2D, depthMap);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, shadowWidth, shadowHeight, 0, GL_DEPTH_COMPONENT, GL_FLOAT, null as FloatArray?)
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
}
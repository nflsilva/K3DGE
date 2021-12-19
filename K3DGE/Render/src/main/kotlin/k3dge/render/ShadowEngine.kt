package k3dge.render

import org.lwjgl.opengl.GL30.*

class ShadowEngine {

    private val depthMap = glGenTextures()
    private val frameBuffer = glGenFramebuffers()

    init {
        createDepthTexture()
        createFrameBufferObject()
    }

    private fun createDepthTexture(){
        glBindTexture(GL_TEXTURE_2D, depthMap);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL_DEPTH_COMPONENT, GL_FLOAT, null as FloatArray?)
    }
    private fun createFrameBufferObject(){
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer)
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMap, 0)
        glDrawBuffer(GL_NONE)
        glReadBuffer(GL_NONE)
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
    }

    companion object {
        val SHADOW_WIDTH = 1024
        val SHADOW_HEIGHT = 1024
    }
}
package k3dge.render.model

import org.lwjgl.opengl.GL13.*
import java.nio.ByteBuffer

class TextureModel(
    width: Int,
    height: Int,
    data: ByteBuffer) {

    val id = glGenTextures()

    init {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data)
        glBindTexture(GL_TEXTURE_2D, 0)
    }
    fun cleanUp(){
        glBindTexture(GL_TEXTURE_2D, 0)
        glDeleteTextures(id)
    }
}
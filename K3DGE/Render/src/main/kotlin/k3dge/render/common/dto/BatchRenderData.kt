package k3dge.render.common.dto

import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import org.lwjgl.opengl.GL11.glBindTexture
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture
import org.lwjgl.opengl.GL20.glDisableVertexAttribArray
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL30.glBindVertexArray

data class BatchRenderData(val vao: Int,
                           val attribArrays: Int,
                           val meshSize: Int,
                           val textureId: Int,
                           val entityData: MutableMap<String, BatchEntityRenderData> = mutableMapOf()){
    fun bind(){
        glBindVertexArray(vao)
        for (i in 0 until attribArrays) {
            glEnableVertexAttribArray(i)
        }
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, textureId)
    }
    fun unbind(){
        for (i in 0 until attribArrays) {
            glDisableVertexAttribArray(i)
        }
        glBindVertexArray(0)
        glBindTexture(GL_TEXTURE_2D, 0)
    }
}
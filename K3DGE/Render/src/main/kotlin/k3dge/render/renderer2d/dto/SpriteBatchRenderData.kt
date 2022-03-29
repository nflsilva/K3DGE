package k3dge.render.renderer2d.dto

import org.joml.Vector2f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30.*
import java.nio.FloatBuffer
import java.nio.IntBuffer

class SpriteBatchRenderData(private val maxQuads: Int) {

    var nIndices: Int = 0
    var nQuads: Int = 0
    private val vao: Int = glGenVertexArrays()
    private val positionsVbo: Int
    private val indexesVbo: Int

    private val positions: FloatBuffer
    private val indices: IntBuffer

    init {
        glBindVertexArray(vao)

        positionsVbo = glGenBuffers()
        positions = BufferUtils.createFloatBuffer(maxQuads * 8)
        glBindBuffer(GL_ARRAY_BUFFER, positionsVbo)
        glBufferData(GL_ARRAY_BUFFER, positions, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)

        indexesVbo = glGenBuffers()
        indices = BufferUtils.createIntBuffer(maxQuads * 6)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexesVbo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_DYNAMIC_DRAW)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun cleanUp(){
        glBindVertexArray(0)
        glDeleteVertexArrays(vao)
        glDeleteBuffers(arrayOf(positionsVbo, indexesVbo).toIntArray())
    }

    fun bind(){

        glBindBuffer(GL_ARRAY_BUFFER, positionsVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, positions.flip())

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexesVbo)
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, indices.flip())

        glBindVertexArray(vao)
        for (i in 0 until 1) {
            glEnableVertexAttribArray(i)
        }
    }
    fun unbind(){
        for (i in 0 until 1) {
            glDisableVertexAttribArray(i)
        }
        glBindVertexArray(0)
    }

    fun appendSpriteData(data: SpriteRenderData){

        if(nQuads >= maxQuads) { return }

        val x = data.position.x
        val y = data.position.y

        val tl = Vector2f(1.0f + x, 14.0f + y)
        val bl = Vector2f(1.0f + x, 1.0f + y)
        val br = Vector2f(14.0f + x, 1.0f + y)
        val tr = Vector2f(14.0f + x, 14.0f + y)

        positions
            .put(tl.x).put(tl.y)
            .put(bl.x).put(bl.y)
            .put(br.x).put(br.y)
            .put(tr.x).put(tr.y)

        val indexOffset = nQuads * 4
        indices
            .put(0 + indexOffset)
            .put(1 + indexOffset)
            .put(2 + indexOffset)

            .put(2 + indexOffset)
            .put(3 + indexOffset)
            .put(0 + indexOffset)

        nQuads += 1
        nIndices += 6
    }
    fun clear() {
        positions.clear()
        indices.clear()
        nIndices = 0
        nQuads = 0
    }
    fun isFull(): Boolean {
        return nQuads == maxQuads
    }

}
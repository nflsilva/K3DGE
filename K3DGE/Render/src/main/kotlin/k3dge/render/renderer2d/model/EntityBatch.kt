package k3dge.render.renderer2d.model

import k3dge.render.common.dto.TransformData
import k3dge.render.common.model.Texture
import k3dge.render.renderer2d.dto.Sprite
import org.joml.Vector2f
import org.lwjgl.BufferUtils
import org.lwjgl.assimp.AIAABB.Buffer
import org.lwjgl.opengl.GL30.*
import java.nio.FloatBuffer
import java.nio.IntBuffer

open class EntityBatch(private val maxEntities: Int,
                       private val nVerticesPerEntity: Int,
                       private val nIndexesPerEntity: Int) {

    private data class BuffersF(val vbo: Int, val buffer: FloatBuffer, val size: Int)
    private data class BuffersI(val vbo: Int, val buffer: IntBuffer)

    var nEntities: Int = 0
    val nIndexes: Int
        get() = this.nEntities * nIndexesPerEntity

    private val vao: Int = glGenVertexArrays()
    private val attributes: MutableMap<Int, BuffersF> = mutableMapOf()

    private val indexesVbo: Int
    private val indices: IntBuffer

    init {
        glBindVertexArray(vao)
        with(initIndexBuffer()){ indexesVbo = vbo; indices = buffer }
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    open fun bind() {

        attributes.forEach { attribute ->
            bindAttributeBuffer(attribute.value.vbo, attribute.value.buffer.flip())
        }

        bindIndexBuffer(indexesVbo, indices .flip())

        glBindVertexArray(vao)
        for (i in 0 until attributes.size) {
            glEnableVertexAttribArray(i)
        }

    }
    open fun unbind() {
        for (i in 0 until attributes.size) {
            glDisableVertexAttribArray(i)
        }
        glBindVertexArray(0)
    }

    fun addAttributeBuffer(index: Int, size: Int) {
        glBindVertexArray(vao)
        val vbo = glGenBuffers()
        val buffer = BufferUtils.createFloatBuffer(maxEntities * nVerticesPerEntity * size)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        attributes[index] = BuffersF(vbo, buffer, size)
    }
    fun addAttributeData(index: Int, vararg data: Float, perVertex: Boolean = true) {
        val adds = if(perVertex) nVerticesPerEntity else 1
        for(i in 0 until adds) {
            data.forEach { value ->
                attributes[index]?.buffer?.put(value)
            }
        }
    }
    fun addIndexData(vararg data: Int) {
        data.forEach { value ->
            indices.put(value)
        }
    }

    open fun clear() {
        attributes.values.forEach { it.buffer.clear() }
        indices.clear()
        nEntities = 0
    }
    fun isFull(): Boolean {
        return nEntities == maxEntities
    }

    fun cleanUp(){
        glBindVertexArray(0)
        glDeleteVertexArrays(vao)
        glDeleteBuffers(attributes.keys.toIntArray())
    }

    private fun initIndexBuffer(): BuffersI {
        val vbo = glGenBuffers()
        val buffer = BufferUtils.createIntBuffer(maxEntities * nIndexesPerEntity)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW)
        return BuffersI(vbo, buffer)
    }
    private fun bindAttributeBuffer(vbo: Int, buffer: FloatBuffer){
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer)
    }
    private fun bindIndexBuffer(vbo: Int, buffer: IntBuffer){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo)
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, buffer)
    }

}
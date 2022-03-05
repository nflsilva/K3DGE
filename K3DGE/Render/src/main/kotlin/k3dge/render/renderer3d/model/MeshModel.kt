package k3dge.render.renderer3d.model

import org.lwjgl.opengl.GL30.*

abstract class MeshModel() {

    val vao: Int = glGenVertexArrays()
    var size: Int = 0
    val attributeArrays: Int
        get() = vbos.size

    private val vbos: MutableList<Int> = mutableListOf()

    fun cleanUp(){
        glBindVertexArray(0)
        glDeleteVertexArrays(vao)
        glDeleteBuffers(vbos.toIntArray())
    }

    protected fun loadIntoAttributeArray(location: Int, length: Int, data: Array<Float>) {
        val vbo: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, data.toFloatArray(), GL_STATIC_DRAW)
        glVertexAttribPointer(location, length, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        vbos.add(vbo)
    }
    protected fun loadIntoIndexBuffer(data: Array<Int>) {
        val vbo: Int = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.toIntArray(), GL_STATIC_DRAW)
        vbos.add(vbo)
        size = data.size
    }
}
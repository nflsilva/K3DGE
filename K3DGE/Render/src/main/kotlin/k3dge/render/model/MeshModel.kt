package k3dge.render.model

import jdk.jfr.Unsigned
import org.lwjgl.opengl.GL30.*

class MeshModel(
    positions: Array<Float>,
    textureCoordinates: Array<Float>,
    normals: Array<Float>,
    indices: Array<Int>) {

    val vao: Int = glGenVertexArrays()
    private val vbos: MutableList<Int> = mutableListOf()
    var size: Int = 0

    init {
        glBindVertexArray(vao)
        loadIntoIndexBuffer(indices)
        loadIntoAttributeList(0, 3, positions)
        loadIntoAttributeList(1, 2, textureCoordinates)
        loadIntoAttributeList(2, 3, normals)
        glBindVertexArray(0)
    }
    fun cleanUp(){
        glBindVertexArray(0)
        glDeleteVertexArrays(vao)
        glDeleteBuffers(vbos.toIntArray())
    }

    private fun loadIntoAttributeList(location: Int, length: Int, data: Array<Float>) {
        val vbo: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, data.toFloatArray(), GL_STATIC_DRAW)
        glVertexAttribPointer(location, length, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        vbos.add(vbo)
    }
    private fun loadIntoIndexBuffer(data: Array<Int>) {
        val vbo: Int = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.toIntArray(), GL_STATIC_DRAW)
        vbos.add(vbo)
        size = data.size
    }
}
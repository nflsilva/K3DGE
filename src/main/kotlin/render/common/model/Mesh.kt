package render.common.model

import org.lwjgl.opengl.GL30.*
import render.common.enum.MeshDimensions
import render.common.enum.MeshUsage
import tools.common.ResourceManager
import tools.common.dto.MeshData

class Mesh(
    dimensions: MeshDimensions,
    usage: MeshUsage,
    indices: MutableList<Int>,
    positions: MutableList<Float>,
    textureCoordinates: MutableList<Float> = mutableListOf(),
    normals: MutableList<Float> = mutableListOf()
) {

    private val vao: Int = glGenVertexArrays()
    var size: Int = 0
    private val vbos: MutableList<Int> = mutableListOf()

    constructor(dimensions: MeshDimensions, usage: MeshUsage, meshData: MeshData) :
            this(dimensions, usage, meshData.indices, meshData.positions, meshData.textureCoordinates, meshData.normals)

    constructor(dimensions: MeshDimensions, usage: MeshUsage, resourceName: String) :
            this(dimensions, usage, ResourceManager.loadMeshFromFile(resourceName))

    init {
        glBindVertexArray(vao)
        loadIntoIndexBuffer(indices.toTypedArray(), usage.value)
        loadIntoAttributeArray(0, dimensions.value, positions.toTypedArray(), usage.value)
        loadIntoAttributeArray(1, 2, textureCoordinates.toTypedArray(), usage.value)
        loadIntoAttributeArray(2, dimensions.value, normals.toTypedArray(), usage.value)
        glBindVertexArray(0)
    }

    fun cleanUp() {
        glBindVertexArray(0)
        glDeleteVertexArrays(vao)
        glDeleteBuffers(vbos.toIntArray())
    }

    fun bind() {
        glBindVertexArray(vao)
        for (i in 0 until vbos.size) {
            glEnableVertexAttribArray(i)
        }
    }

    fun unbind() {
        for (i in 0 until vbos.size) {
            glDisableVertexAttribArray(i)
        }
        glBindVertexArray(0)
    }

    private fun loadIntoAttributeArray(location: Int, length: Int, data: Array<Float>, usage: Int) {
        val vbo: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, data.toFloatArray(), usage)
        glVertexAttribPointer(location, length, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        vbos.add(vbo)
    }

    private fun loadIntoIndexBuffer(data: Array<Int>, usage: Int) {
        val vbo: Int = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.toIntArray(), usage)
        vbos.add(vbo)
        size = data.size
    }

    companion object {
        fun initQuad(): Mesh {
            return Mesh(
                MeshDimensions.D2,
                MeshUsage.STATIC,
                mutableListOf(0, 1, 2, 3),
                mutableListOf(-1F, 1F, -1F, -1F, 1F, 1F, 1F, -1F)
            )
        }
    }

}
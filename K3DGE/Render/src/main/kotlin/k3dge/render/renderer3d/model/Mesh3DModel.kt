package k3dge.render.renderer3d.model

import org.lwjgl.opengl.GL30

class Mesh3DModel(positions: Array<Float>,
                  textureCoordinates: Array<Float>,
                  normals: Array<Float>,
                  indices: Array<Int>): MeshModel() {

    init {
        GL30.glBindVertexArray(vao)
        loadIntoIndexBuffer(indices)
        loadIntoAttributeArray(0, 3, positions)
        loadIntoAttributeArray(1, 2, textureCoordinates)
        loadIntoAttributeArray(2, 3, normals)
        GL30.glBindVertexArray(0)
    }
}
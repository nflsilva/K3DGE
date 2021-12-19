package k3dge.render.model

import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL30

class Mesh3DModel(positions: Array<Float>,
                  textureCoordinates: Array<Float>,
                  normals: Array<Float>,
                  indices: Array<Int>): MeshModel() {

    init {
        GL30.glBindVertexArray(vao)
        loadIntoIndexBuffer(indices)
        loadIntoAttributeList(0, 3, positions)
        loadIntoAttributeList(1, 2, textureCoordinates)
        loadIntoAttributeList(2, 3, normals)
        GL30.glBindVertexArray(0)
    }
}
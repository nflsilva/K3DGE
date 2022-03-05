package k3dge.render.renderer3d.model

import org.lwjgl.opengl.GL30.*

class MeshGuiModel: MeshModel() {

    init {
        glBindVertexArray(vao)
        loadIntoIndexBuffer(arrayOf(0, 1, 2, 3))
        loadIntoAttributeArray(0, 2, arrayOf(-1F, 1F, -1F, -1F, 1F, 1F, 1F, -1F))
        glBindVertexArray(0)
    }
}
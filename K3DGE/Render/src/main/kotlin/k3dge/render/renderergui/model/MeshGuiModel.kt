package k3dge.render.renderergui.model

import k3dge.render.common.model.MeshModel
import org.lwjgl.opengl.GL30.*

class MeshGuiModel: MeshModel() {

    init {
        glBindVertexArray(vao)
        loadIntoIndexBuffer(arrayOf(0, 1, 2, 3))
        loadIntoAttributeArray(0, 2, arrayOf(-1F, 1F, -1F, -1F, 1F, 1F, 1F, -1F))
        glBindVertexArray(0)
    }
}
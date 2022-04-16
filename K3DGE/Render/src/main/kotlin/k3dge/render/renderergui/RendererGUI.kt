package k3dge.render.renderergui

import k3dge.configuration.EngineConfiguration
import k3dge.render.common.dto.ShaderUniformData
import k3dge.render.common.dto.TransformData
import k3dge.render.common.model.Mesh
import k3dge.render.common.model.Shader
import k3dge.render.common.model.Texture
import k3dge.render.renderer3d.Renderer3D
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11.*

class RendererGUI(private val configuration: EngineConfiguration) {

    private data class ObjectRenderData(val mesh: Mesh,
                                        val texture: Texture,
                                        var shader: Shader,
                                        val modelMatrix: Matrix4f
    )

    private val objectsData: MutableList<Renderer3D.ObjectRenderData> = mutableListOf()

    fun renderGui(mesh: Mesh, texture: Texture, transform: TransformData){
        addEntityToRenderList(model)
    }
    fun onFrame() {
        drawGuis()
    }
    fun onCleanUp(){}

    private fun drawGuis(){
        for(batch in renderBatches.values){
            batch.bind()
            for(entity in batch.entityData){
                entity.value.prepareShader(ShaderUniformData(entity.value.modelMatrix))
                glDrawElements(GL_TRIANGLE_STRIP, batch.meshSize, GL_UNSIGNED_INT, 0)
            }
            batch.unbind()
        }
    }

}
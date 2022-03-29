package k3dge.render.renderergui

import k3dge.configuration.EngineConfiguration
import k3dge.render.BaseRenderer
import k3dge.render.renderer3d.dto.EntityRenderData
import k3dge.render.renderer3d.dto.ShaderUniformData
import org.lwjgl.opengl.GL11.*

class RendererGUI(private val configuration: EngineConfiguration): BaseRenderer() {

    fun renderGui(model: EntityRenderData){
        addEntityToRenderList(model)
    }

    fun onFrame() {
        drawGuis()
    }

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
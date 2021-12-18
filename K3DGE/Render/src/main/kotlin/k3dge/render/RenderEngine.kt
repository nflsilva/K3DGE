package k3dge.render

import k3dge.render.dto.CameraRenderData
import k3dge.render.dto.LightRenderData
import k3dge.render.dto.TexturedMeshRenderData
import k3dge.render.model.ShaderModel
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL30.*
import java.util.*

class RenderEngine {

    private val renderBatches: MutableMap<String, RenderBatchData> = mutableMapOf()
    private val directionalLights: MutableList<LightRenderData> = mutableListOf()
    private var projectionMatrix: Matrix4f = Matrix4f()
        .setPerspective(
            1.25F,
            SCREEN_WIDTH.toFloat() / SCREEN_HEIGHT.toFloat(),
            0.1F,
            100F)
    private var viewMatrix: Matrix4f = Matrix4f().identity()

    fun renderCamera(cameraData: CameraRenderData){
        viewMatrix = Matrix4f().lookAt(cameraData.position, cameraData.lookAt, cameraData.up)
    }
    fun renderDirectionalLight(light: LightRenderData) {
        directionalLights.add(light)
    }
    fun renderTexturedMesh(model: TexturedMeshRenderData){

        val modelMatrix = Matrix4f()
        modelMatrix.translation(model.position)
        modelMatrix.rotate(model.rotation.x, Vector3f(1.0f, 0.0f, 0.0f))
        modelMatrix.rotate(model.rotation.y, Vector3f(0.0f, 1.0f, 0.0f))
        modelMatrix.rotate(model.rotation.z, Vector3f(0.0f, 0.0f, 1.0f))
        modelMatrix.scale(model.scale)

        val batchId = model.componentId.toString()
        val entityId = model.entityId.toString()

        if(!renderBatches.containsKey(batchId)){
            renderBatches[batchId] = RenderBatchData(model.mesh.vao, model.mesh.size, model.texture.id)
        }
        val batchData: RenderBatchData = renderBatches[batchId]!!
        if(!batchData.entityData.containsKey(entityId)) {
            renderBatches[batchId]!!.entityData[entityId] = EntityRenderData(model.entityId, model.shader, modelMatrix)
        }
        else {
            renderBatches[batchId]!!.entityData[entityId]!!.modelMatrix = modelMatrix
        }

    }
    fun onStart() {
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)
    }
    fun onFrame() {
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        drawBatches()
    }

    private fun drawBatches(){
        for(batch in renderBatches.values){
            bindTexturedModelFromBatch(batch)
            for(entity in batch.entityData){
                prepareEntityShader(entity.value)
                glDrawElements(GL_TRIANGLES, batch.meshSize, GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModelFromBatch()
        }
    }
    private fun bindTexturedModelFromBatch(batch: RenderBatchData){
        glBindVertexArray(batch.vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, batch.textureId);
    }
    private fun unbindTexturedModelFromBatch(){
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)
        glBindVertexArray(0)
        glBindTexture(GL_TEXTURE_2D, 0)
    }
    private fun prepareEntityShader(entity: EntityRenderData){
        entity.shader.bind()
        entity.shader.setModelMatrix(entity.modelMatrix)
        entity.shader.setProjectionMatrix(projectionMatrix)
        entity.shader.setViewMatrix(viewMatrix)
        directionalLights.forEach { light ->
            entity.shader.setLightDirection(light.position)
            entity.shader.setLightColor(light.color)
        }
    }

    private data class EntityRenderData(
        val id: UUID,
        val shader: ShaderModel,
        var modelMatrix: Matrix4f
    )
    private data class RenderBatchData(
        val vao: Int,
        val meshSize: Int,
        val textureId: Int,
        val entityData: MutableMap<String, EntityRenderData> = mutableMapOf()
    )

    companion object {
        const val SCREEN_WIDTH: Int = 1280
        const val SCREEN_HEIGHT: Int = 720
    }
}
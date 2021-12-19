package k3dge.render

import k3dge.render.dto.CameraRenderData
import k3dge.render.dto.LightRenderData
import k3dge.render.dto.ShaderUniformData
import k3dge.render.model.ShaderModel
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL30.*
import java.util.*

class RenderEngine {

    private val texturedBatches: MutableMap<String, RenderBatchData> = mutableMapOf()
    private val guiBatches: MutableMap<String, RenderBatchData> = mutableMapOf()
    private val lights: MutableMap<String, LightRenderData> = mutableMapOf()

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
        lights[light.uid.toString()] = light
    }

    //TODO: Refactor these 2 methods into one
    fun renderTexturedMesh(model: k3dge.render.dto.EntityRenderData){

        val modelMatrix = computeModelMatrix(model.position, model.rotation, model.scale)
        val batchId = model.componentId.toString()
        val entityId = model.entityId.toString()

        if(!texturedBatches.containsKey(batchId)){
            texturedBatches[batchId] = RenderBatchData(model.mesh.vao, model.mesh.size, model.texture.id)
        }
        val batchData: RenderBatchData = texturedBatches[batchId]!!
        if(!batchData.entityData.containsKey(entityId)) {
            texturedBatches[batchId]!!.entityData[entityId] = EntityRenderData(model.entityId, model.shader, modelMatrix)
        }
        else {
            texturedBatches[batchId]!!.entityData[entityId]!!.modelMatrix = modelMatrix
        }
    }
    fun renderGui(model: k3dge.render.dto.EntityRenderData){
        val modelMatrix = computeModelMatrix(model.position, model.rotation, model.scale)
        val batchId = model.componentId.toString()
        val entityId = model.entityId.toString()

        if(!guiBatches.containsKey(batchId)){
            guiBatches[batchId] = RenderBatchData(model.mesh.vao, model.mesh.size, model.texture.id)
        }
        val batchData: RenderBatchData = guiBatches[batchId]!!
        if(!batchData.entityData.containsKey(entityId)) {
            guiBatches[batchId]!!.entityData[entityId] = EntityRenderData(model.entityId, model.shader, modelMatrix)
        }
        else {
            guiBatches[batchId]!!.entityData[entityId]!!.modelMatrix = modelMatrix
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
        drawGuis()
    }

    private fun drawBatches(){
        for(batch in texturedBatches.values){
            bindTexturedModelFromBatch(batch)
            for(entity in batch.entityData){
                prepareShader(entity.value)
                glDrawElements(GL_TRIANGLES, batch.meshSize, GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModelFromBatch()
        }
    }
    private fun drawGuis(){
        for(batch in guiBatches.values){
            bindTexturedModelFromBatch(batch)
            for(entity in batch.entityData){
                prepareShader(entity.value)
                glDrawElements(GL_TRIANGLE_STRIP, batch.meshSize, GL_UNSIGNED_INT, 0);
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
    private fun prepareShader(entity: EntityRenderData){
        entity.shader.bind()
        //TODO: We may need multiple lights in the future...
        var light: LightRenderData? = null
        lights.keys.forEach { light = lights[it] }

        entity.shader.updateUniforms(ShaderUniformData(
            entity.modelMatrix,
            viewMatrix,
            projectionMatrix,
            light?.position,
            light?.color
        ))
    }

    private fun computeModelMatrix(position: Vector3f, rotation: Vector3f, scale: Vector3f): Matrix4f{
        val modelMatrix = Matrix4f()
        modelMatrix.translation(position)
        modelMatrix.rotate(rotation.x, Vector3f(1.0f, 0.0f, 0.0f))
        modelMatrix.rotate(rotation.y, Vector3f(0.0f, 1.0f, 0.0f))
        modelMatrix.rotate(rotation.z, Vector3f(0.0f, 0.0f, 1.0f))
        modelMatrix.scale(scale)
        return modelMatrix
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
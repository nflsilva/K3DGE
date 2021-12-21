package k3dge.render

import k3dge.render.dto.CameraRenderData
import k3dge.render.dto.EntityRenderData
import k3dge.render.dto.LightRenderData
import k3dge.render.dto.ShaderUniformData
import k3dge.render.model.ShaderModel
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL30.*
import java.util.*

class RenderEngine {

    private val texturedBatches: MutableMap<String, BatchRenderData> = mutableMapOf()
    private val guiBatches: MutableMap<String, BatchRenderData> = mutableMapOf()
    private val lights: MutableMap<String, LightRenderData> = mutableMapOf()

    private var backgroundColor: Vector4f = Vector4f(1.0f)
    private var cameraPosition: Vector3f = Vector3f(0.0F)
    private var shadowRenderer: ShadowEngine? = null

    private var projectionMatrix: Matrix4f = Matrix4f()
        .setPerspective(
            1.25F,
            SCREEN_WIDTH.toFloat() / SCREEN_HEIGHT.toFloat(),
            0.1F,
            100F)
    private var viewMatrix: Matrix4f = Matrix4f().identity()

    fun renderCamera(cameraData: CameraRenderData){
        cameraPosition = cameraData.position
        viewMatrix = Matrix4f().lookAt(cameraData.position, cameraData.lookAt, cameraData.up)
    }
    fun renderDirectionalLight(light: LightRenderData) {
        lights[light.uid.toString()] = light
    }

    //TODO: Refactor these 2 methods into one
    fun renderTexturedMesh(model: EntityRenderData){

        val modelMatrix = computeModelMatrix(model.position, model.rotation, model.scale)
        val batchId = model.componentId.toString()
        val entityId = model.entityId.toString()

        if(!texturedBatches.containsKey(batchId)){
            texturedBatches[batchId] = BatchRenderData(model.mesh.vao, model.mesh.size, model.texture.id)
        }
        val batchData: BatchRenderData = texturedBatches[batchId]!!
        if(!batchData.entityData.containsKey(entityId)) {
            texturedBatches[batchId]!!.entityData[entityId] = BatchEntityRenderData(model.entityId, model.shader, modelMatrix)
        }
        else {
            texturedBatches[batchId]!!.entityData[entityId]!!.modelMatrix = modelMatrix
        }
    }
    fun renderGui(model: EntityRenderData){
        val modelMatrix = computeModelMatrix(model.position, model.rotation, model.scale)
        val batchId = model.componentId.toString()
        val entityId = model.entityId.toString()

        if(!guiBatches.containsKey(batchId)){
            guiBatches[batchId] = BatchRenderData(model.mesh.vao, model.mesh.size, model.texture.id)
        }
        val batchData: BatchRenderData = guiBatches[batchId]!!
        if(!batchData.entityData.containsKey(entityId)) {
            guiBatches[batchId]!!.entityData[entityId] = BatchEntityRenderData(model.entityId, model.shader, modelMatrix)
        }
        else {
            guiBatches[batchId]!!.entityData[entityId]!!.modelMatrix = modelMatrix
        }
    }
    fun setBackgroundColor(color: Vector4f){
        backgroundColor = color
    }

    fun onStart() {
        shadowRenderer = ShadowEngine()
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)
    }
    fun onFrame() {
        glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, backgroundColor.w)
        computeShadowMap()

        glBindFramebuffer(GL_FRAMEBUFFER, 0)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        //TODO: Need to refactor these parameters. Maybe get them from a config file?
        glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)
        drawBatches()
        drawGuis()
    }

    private fun computeShadowMap(){
        //TODO: Refactor this method.
        shadowRenderer?.let { it ->

            var light: LightRenderData? = null
            lights.keys.forEach { light = lights[it] }
            if(light == null) return

            it.bindFramebuffer()
            glClear(GL_DEPTH_BUFFER_BIT)
            it.prepareLightSpaceMatrix(light!!.position, cameraPosition)
            for(batch in texturedBatches.values){
                glBindVertexArray(batch.vao)
                glEnableVertexAttribArray(0)
                for(entity in batch.entityData){
                    it.updateUniforms(entity.value.modelMatrix)
                    glDrawElements(GL_TRIANGLES, batch.meshSize, GL_UNSIGNED_INT, 0);
                }
            }
            it.unbindFramebuffer()
        }
    }
    private fun drawBatches(){
        for(batch in texturedBatches.values){
            bindTexturedModelFromBatch(batch)

            glActiveTexture(GL_TEXTURE0)
            glBindTexture(GL_TEXTURE_2D, batch.textureId)
            glActiveTexture(GL_TEXTURE1)
            shadowRenderer?.bindDepthMap()

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

            //FIXME: Shadow debug hack
            glActiveTexture(GL_TEXTURE0)
            shadowRenderer?.bindDepthMap()

            for(entity in batch.entityData){
                prepareShader(entity.value)
                glDrawElements(GL_TRIANGLE_STRIP, batch.meshSize, GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModelFromBatch()
        }
    }
    private fun bindTexturedModelFromBatch(batch: BatchRenderData){
        //TODO: Should this be added to the model?
        glBindVertexArray(batch.vao)
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)
    }
    private fun unbindTexturedModelFromBatch(){
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)
        glBindVertexArray(0)
        glBindTexture(GL_TEXTURE_2D, 0)
    }
    private fun prepareShader(entity: BatchEntityRenderData){
        entity.shader.bind()
        //TODO: We may need multiple lights in the future...
        var light: LightRenderData? = null
        lights.keys.forEach { light = lights[it] }

        entity.shader.updateUniforms(ShaderUniformData(
            entity.modelMatrix,
            viewMatrix,
            projectionMatrix,
            light?.position,
            light?.color,
            shadowRenderer?.lightSpaceMatrix
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

    private data class BatchEntityRenderData(
        val id: UUID,
        val shader: ShaderModel,
        var modelMatrix: Matrix4f
    )
    private data class BatchRenderData(
        val vao: Int,
        val meshSize: Int,
        val textureId: Int,
        val entityData: MutableMap<String, BatchEntityRenderData> = mutableMapOf()
    )

    companion object {
        const val SCREEN_WIDTH: Int = 1280
        const val SCREEN_HEIGHT: Int = 720
    }
}
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
    private var light: LightRenderData? = null

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
        this.light = light
    }

    private fun addEntityToRenderList(batches: MutableMap<String, BatchRenderData>, model: EntityRenderData) {
        val modelMatrix = computeModelMatrix(model.position, model.rotation, model.scale)
        val batchId = model.componentId.toString()
        val entityId = model.entityId.toString()

        if(!batches.containsKey(batchId)){
            batches[batchId] = BatchRenderData(model.mesh.vao, model.mesh.attributeArrays, model.mesh.size, model.texture.id)
        }
        val batchData: BatchRenderData = batches[batchId]!!
        if(!batchData.entityData.containsKey(entityId)) {
            batches[batchId]!!.entityData[entityId] = BatchEntityRenderData(model.entityId, model.shader, modelMatrix)
        }
        else {
            batches[batchId]!!.entityData[entityId]!!.modelMatrix = modelMatrix
        }
    }
    fun renderTexturedMesh(model: EntityRenderData){
        addEntityToRenderList(texturedBatches, model)
    }
    fun renderGui(model: EntityRenderData){
        addEntityToRenderList(guiBatches, model)
    }
    fun setBackgroundColor(color: Vector4f){
        backgroundColor = color
    }

    fun onStart() {
        shadowRenderer = ShadowEngine()
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_CULL_FACE)
    }
    fun onFrame() {

        glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, backgroundColor.w)
        computeShadowMap()

        //TODO:[Config File]
        glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glCullFace(GL_BACK)

        drawBatches()
        drawGuis()
    }

    private fun computeShadowMap(){

        shadowRenderer?.let { it ->

            if(light == null) return

            it.bindFramebuffer()
            glClear(GL_DEPTH_BUFFER_BIT)
            glCullFace(GL_FRONT)
            it.updateLightSpaceMatrix(light!!.position, cameraPosition)

            for(batch in texturedBatches.values){
                batch.bind()
                for(entity in batch.entityData){
                    it.updateUniforms(entity.value.modelMatrix)
                    glDrawElements(GL_TRIANGLES, batch.meshSize, GL_UNSIGNED_INT, 0);
                }
                batch.unbind()
            }
            it.unbindFramebuffer()
        }
    }
    private fun drawBatches(){
        for(batch in texturedBatches.values){
            batch.bind()
            shadowRenderer?.bindDepthMap(1)
            for(entity in batch.entityData){
                prepareShader(entity.value)
                glDrawElements(GL_TRIANGLES, batch.meshSize, GL_UNSIGNED_INT, 0);
            }
            batch.unbind()
        }
    }
    private fun drawGuis(){
        for(batch in guiBatches.values){
            batch.bind()
            shadowRenderer?.bindDepthMap(0)

            for(entity in batch.entityData){
                prepareShader(entity.value)
                glDrawElements(GL_TRIANGLE_STRIP, batch.meshSize, GL_UNSIGNED_INT, 0);
            }
            batch.unbind()
        }
    }

    private fun prepareShader(entity: BatchEntityRenderData){
        entity.shader.bind()
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
    private data class BatchRenderData(val vao: Int,
                                       val attribArrays: Int,
                                       val meshSize: Int,
                                       val textureId: Int,
                                       val entityData: MutableMap<String, BatchEntityRenderData> = mutableMapOf()
    ){

        fun bind(){
            glBindVertexArray(vao)
            for (i in 0 until attribArrays) {
                glEnableVertexAttribArray(i)
            }
            glActiveTexture(GL_TEXTURE0)
            glBindTexture(GL_TEXTURE_2D, textureId)
        }
        fun unbind(){
            for (i in 0 until attribArrays) {
                glDisableVertexAttribArray(i)
            }
            glBindVertexArray(0)
            glBindTexture(GL_TEXTURE_2D, 0)
        }
    }

    companion object {
        //TODO: [Config File]
        const val SCREEN_WIDTH: Int = 1280
        const val SCREEN_HEIGHT: Int = 720
    }
}
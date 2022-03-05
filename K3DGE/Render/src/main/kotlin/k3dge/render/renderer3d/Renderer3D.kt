package k3dge.render.renderer3d

import k3dge.configuration.EngineConfiguration
import k3dge.render.BaseRenderer
import k3dge.render.dto.BatchEntityRenderData
import k3dge.render.dto.BatchRenderData
import k3dge.render.renderer3d.dto.CameraRenderData
import k3dge.render.renderer3d.dto.EntityRenderData
import k3dge.render.renderer3d.dto.LightRenderData
import k3dge.render.renderer3d.dto.ShaderUniformData
import k3dge.render.renderer3d.model.ShaderModel
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL30.*
import java.util.*

class Renderer3D(private val configuration: EngineConfiguration): BaseRenderer() {

    private var light: LightRenderData? = null
    private var cameraPosition: Vector3f = Vector3f(0.0F)
    private var shadowRenderer: RendererShadow? = null

    private var projectionMatrix: Matrix4f = Matrix4f()
        .setPerspective(
            1.25F,
            configuration.resolutionWidth.toFloat() / configuration.resolutionHeight.toFloat(),
            0.1F,
            configuration.renderDistance.toFloat())
    private var viewMatrix: Matrix4f = Matrix4f().identity()

    fun renderCamera(cameraData: CameraRenderData){
        cameraPosition = cameraData.position
        viewMatrix = Matrix4f().lookAt(cameraData.position, cameraData.lookAt, cameraData.up)
    }
    fun renderDirectionalLight(light: LightRenderData) {
        this.light = light
    }
    fun renderTexturedMesh(model: EntityRenderData){
        addEntityToRenderList(model)
    }

    fun onStart() {
        if(configuration.enableShadows){
            shadowRenderer = RendererShadow(configuration.shadowResolutionWidth, configuration.shadowResolutionHeight)
        }
        glEnable(GL_DEPTH_TEST)
    }
    fun onFrame() {

        computeShadowMap()

        glViewport(0, 0, configuration.resolutionWidth, configuration.resolutionHeight)
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)

        drawBatches()
    }

    private fun computeShadowMap(){

        shadowRenderer?.let { it ->

            if(light == null) return

            it.bindFramebuffer()
            glClear(GL_DEPTH_BUFFER_BIT)
            glCullFace(GL_FRONT)
            it.updateLightSpaceMatrix(light!!.position, cameraPosition)

            for(batch in renderBatches.values){
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
        for(batch in renderBatches.values){
            batch.bind()
            shadowRenderer?.bindDepthMap(1)
            for(entity in batch.entityData){
                entity.value.prepareShader(
                    ShaderUniformData(entity.value.modelMatrix,
                                    viewMatrix,
                                    projectionMatrix,
                                    light?.position,
                                    light?.color,
                                    shadowRenderer?.lightSpaceMatrix))
                glDrawElements(GL_TRIANGLES, batch.meshSize, GL_UNSIGNED_INT, 0);
            }
            batch.unbind()
        }
    }

}
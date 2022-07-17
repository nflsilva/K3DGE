package k3dge.render.renderer3d

import k3dge.configuration.EngineConfiguration
import k3dge.render.renderer3d.dto.CameraData
import k3dge.render.renderer3d.dto.LightData
import k3dge.render.common.dto.ShaderUniformData
import k3dge.render.common.dto.TransformData
import k3dge.render.common.shader.Shader
import k3dge.render.common.model.Mesh
import k3dge.render.common.model.Texture
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL30.*

class Renderer3D(private val configuration: EngineConfiguration) {

    private data class ObjectRenderData(val mesh: Mesh,
                                        val texture: Texture,
                                        var shader: Shader,
                                        val modelMatrix: Matrix4f)

    private val objectsData: MutableList<ObjectRenderData> = mutableListOf()
    private var light: LightData? = null
    private var cameraPosition: Vector3f = Vector3f(0.0F)
    private var shadowRenderer: RendererShadow? = null

    private var projectionMatrix: Matrix4f = Matrix4f()
        .setPerspective(
            1.25F,
            configuration.resolutionWidth.toFloat() / configuration.resolutionHeight.toFloat(),
            0.1F,
            configuration.renderDistance.toFloat())
    private var viewMatrix: Matrix4f = Matrix4f().identity()

    fun renderCamera(cameraData: CameraData){
        cameraPosition = cameraData.position
        viewMatrix = Matrix4f().lookAt(cameraData.position, cameraData.lookAt, cameraData.up)
    }
    fun renderDirectionalLight(light: LightData) {
        this.light = light
    }
    fun renderTexturedMesh(mesh: Mesh, texture: Texture, shader: Shader, transform: TransformData){
        val modelMatrix = computeModelMatrix(transform)
        objectsData.add(ObjectRenderData(mesh, texture, shader, modelMatrix))
    }

    fun onStart() {
        if(configuration.enableShadows){
            shadowRenderer = RendererShadow(configuration.shadowResolutionWidth, configuration.shadowResolutionHeight)
        }
        glEnable(GL_DEPTH_TEST)
    }
    fun onFrame() {
        computeShadowMap()
        drawObjects()
    }
    fun onUpdate() {
        objectsData.clear()
    }
    fun onCleanUp(){}

    private fun computeShadowMap(){
        shadowRenderer?.let { it ->

            if(light == null) return

            it.bindFramebuffer()
            glViewport(0, 0, configuration.shadowResolutionWidth, configuration.shadowResolutionHeight)
            glClear(GL_DEPTH_BUFFER_BIT)
            glDisable(GL_CULL_FACE)
            it.updateLightSpaceMatrix(light!!.position, cameraPosition)

            for(entity in objectsData) {
                entity.mesh.bind()

                it.updateUniforms(entity.modelMatrix)
                glDrawElements(GL_TRIANGLES, entity.mesh.size, GL_UNSIGNED_INT, 0)

                entity.mesh.unbind()
            }
            it.unbindFramebuffer()
        }
    }
    private fun drawObjects(){

        glViewport(0, 0, configuration.resolutionWidth, configuration.resolutionHeight)
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)

        shadowRenderer?.bindDepthMap(1)
        for(entity in objectsData) {

            entity.mesh.bind()
            entity.texture.bind(0)
            entity.shader.bind()
            entity.shader.updateUniforms(
                ShaderUniformData(entity.modelMatrix,
                    viewMatrix,
                    projectionMatrix,
                    light?.position,
                    light?.color,
                    shadowRenderer?.lightSpaceMatrix))

            glDrawElements(GL_TRIANGLES, entity.mesh.size, GL_UNSIGNED_INT, 0)

            entity.mesh.unbind()
            entity.texture.unbind()
            entity.shader.unbind()
        }
    }
    private fun computeModelMatrix(transform: TransformData): Matrix4f {
        val modelMatrix = Matrix4f()
        modelMatrix.translation(transform.position)
        modelMatrix.rotate(transform.rotation.x, Vector3f(1.0f, 0.0f, 0.0f))
        modelMatrix.rotate(transform.rotation.y, Vector3f(0.0f, 1.0f, 0.0f))
        modelMatrix.rotate(transform.rotation.z, Vector3f(0.0f, 0.0f, 1.0f))
        modelMatrix.scale(transform.scale)
        return modelMatrix
    }

}
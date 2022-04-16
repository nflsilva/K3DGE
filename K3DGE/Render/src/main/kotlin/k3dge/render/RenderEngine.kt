package k3dge.render

import k3dge.configuration.EngineConfiguration
import k3dge.render.renderergui.RendererGUI
import k3dge.render.renderer2d.Renderer2D
import k3dge.render.renderer3d.Renderer3D
import k3dge.render.common.dto.CameraData
import k3dge.render.renderer2d.model.Sprite
import k3dge.render.common.dto.TransformData
import k3dge.render.common.model.Mesh
import k3dge.render.common.model.Shader
import k3dge.render.common.model.Texture
import k3dge.render.renderer3d.dto.StaticObjectData
import k3dge.render.renderer3d.dto.LightData
import org.joml.Vector4f
import org.lwjgl.opengl.GL30.glClearColor

class RenderEngine(configuration: EngineConfiguration) {

    private var backgroundColor: Vector4f = Vector4f(0.0f)

    private val rendererGUI: RendererGUI?
    private var renderer2D: Renderer2D? = null
    private var renderer3D: Renderer3D? = null

    init {
        if(configuration.is3D){
            renderer3D = Renderer3D(configuration)
        }
        else {
            renderer2D = Renderer2D(configuration)
        }
        rendererGUI = RendererGUI(configuration)
    }

    fun setBackgroundColor(color: Vector4f){
        backgroundColor = color
    }

    fun onStart() {
        renderer2D?.onStart()
        renderer3D?.onStart()
    }
    fun onFrame() {
        glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, backgroundColor.w)
        renderer3D?.onFrame()
        renderer2D?.onFrame()
        rendererGUI?.onFrame()
    }
    fun onUpdate() {
        renderer2D?.onUpdate()
    }
    fun onCleanUp() {
        renderer3D?.onCleanUp()
        renderer2D?.onCleanUp()
        rendererGUI?.onCleanUp()
    }

    fun renderCamera(cameraData: CameraData){
        renderer3D?.renderCamera(cameraData)
    }
    fun renderDirectionalLight(light: LightData) {
        renderer3D?.renderDirectionalLight(light)
    }
    fun renderTexturedMesh(mesh: Mesh, texture: Texture, shader: Shader, transform: TransformData){
        renderer3D?.renderTexturedMesh(mesh, texture, shader, transform)
    }
    fun renderGui(mesh: Mesh, texture: Texture, shader: Shader, transform: TransformData){
        rendererGUI?.renderGui(model)
    }

    fun renderSprite(transform: TransformData, model: Sprite) {
        renderer2D?.renderSprite(transform, model)
    }

}
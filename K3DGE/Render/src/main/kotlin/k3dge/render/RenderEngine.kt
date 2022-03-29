package k3dge.render

import k3dge.configuration.EngineConfiguration
import k3dge.render.renderergui.RendererGUI
import k3dge.render.renderer2d.Renderer2D
import k3dge.render.renderer3d.Renderer3D
import k3dge.render.common.dto.CameraRenderData
import k3dge.render.renderer2d.dto.SpriteRenderData
import k3dge.render.renderer3d.dto.EntityRenderData
import k3dge.render.renderer3d.dto.LightRenderData
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

    fun renderCamera(cameraData: CameraRenderData){
        renderer3D?.renderCamera(cameraData)
    }
    fun renderDirectionalLight(light: LightRenderData) {
        renderer3D?.renderDirectionalLight(light)
    }
    fun renderTexturedMesh(model: EntityRenderData){
        renderer3D?.renderTexturedMesh(model)
    }

    fun renderGui(model: EntityRenderData){
        rendererGUI?.renderGui(model)
    }

    fun renderSprite(model: SpriteRenderData) {
        renderer2D?.renderSprite(model)
    }

}
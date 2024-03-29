package render

import org.joml.Vector4f
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.glClearColor
import render.common.dto.TransformData
import render.common.model.Mesh
import render.common.model.Texture
import render.common.shader.Shader
import render.renderer2d.Renderer2D
import render.renderer2d.dto.Particle
import render.renderer2d.dto.Shape
import render.renderer2d.dto.Sprite
import render.renderer2d.dto.Transform2DData
import render.renderer2d.model.SpriteSizeEnum
import render.renderer3d.Renderer3D
import render.renderer3d.dto.CameraData
import render.renderer3d.dto.LightData
import render.renderer3d.dto.Transform3DData
import tools.configuration.EngineConfiguration

class RenderEngine(configuration: EngineConfiguration) {

    private var backgroundColor: Vector4f = Vector4f(0.0f)

    private var renderer2D: Renderer2D
    private var renderer3D: Renderer3D

    init {
        renderer3D = Renderer3D(configuration)
        renderer2D = Renderer2D(configuration)
    }

    fun setBackgroundColor(color: Vector4f) {
        backgroundColor = color
    }

    fun onStart() {
        renderer2D.onStart()
        renderer3D.onStart()
    }

    fun onFrame() {

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, backgroundColor.w)

        renderer3D.onFrame()
        renderer2D.onFrame()

    }

    fun onUpdate() {
        renderer3D.onUpdate()
        renderer2D.onUpdate()
    }

    fun onCleanUp() {
        renderer3D.onCleanUp()
        renderer2D.onCleanUp()
    }

    fun renderCamera(cameraData: CameraData) {
        renderer3D.renderCamera(cameraData)
    }

    fun renderDirectionalLight(light: LightData) {
        renderer3D.renderDirectionalLight(light)
    }

    fun render3D(mesh: Mesh, texture: Texture, shader: Shader, transform: TransformData) {
        val t = transform as? Transform3DData ?: return
        renderer3D.renderTexturedMesh(mesh, texture, shader, t)
    }

    fun render2D(texture: Texture, transform: TransformData) {
        val t = transform as? Transform2DData ?: return
        renderer2D.renderSprite(Sprite(SpriteSizeEnum.X16, texture), t)
    }

    fun render2D(sprite: Sprite, transform: TransformData) {
        val t = transform as? Transform2DData ?: return
        renderer2D.renderSprite(sprite, t)
    }

    fun render2D(particle: Particle, transform: TransformData) {
        val t = transform as? Transform2DData ?: return
        renderer2D.renderParticle(particle, t)
    }

    fun render2D(shape: Shape, transform: TransformData) {
        val t = transform as? Transform2DData ?: return
        renderer2D.renderShape(shape, t)
    }

}
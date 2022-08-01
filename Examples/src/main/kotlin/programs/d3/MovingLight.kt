package programs.d3

import core.CoreEngine
import core.CoreEngineDelegate
import core.camera.Camera
import core.camera.component.RotateCameraComponent
import core.camera.component.TranslateCameraComponent
import core.camera.component.ZoomCameraComponent
import core.entity.Entity3D
import core.entity.component3d.TexturedMeshEntityComponent
import core.light.Light
import core.light.component.ColorLightComponent
import core.light.component.DirectionalLightComponent
import core.light.component.LightRotateLightComponent
import org.joml.Vector3f
import org.joml.Vector4f
import render.common.enum.MeshDimensions
import render.common.enum.MeshUsage
import render.common.model.Mesh
import render.common.model.Texture
import ui.dto.InputStateData

fun main(args: Array<String>) {

    val engine = CoreEngine()
    val gameLogic = MovingLight(engine)
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class MovingLight(private val engine: CoreEngine) : CoreEngineDelegate {

    override fun onStart() {

        val terrainMesh = Mesh(MeshDimensions.D3, MeshUsage.STATIC, "/mesh/terrain.obj")
        val terrainTexture = Texture("/texture/lowPolyAtlas.png")
        val terrainMeshComp = TexturedMeshEntityComponent(terrainMesh, terrainTexture)
        val terrain = Entity3D(
            Vector3f(0f),
            Vector3f(0f),
            Vector3f(1f, 1f, 1f)).apply {
            addComponent(terrainMeshComp)
        }
        engine.addEntity(terrain)

        val cubeMesh = Mesh(MeshDimensions.D3, MeshUsage.STATIC, "/mesh/cube.obj")
        val boxTexture = Texture("/texture/box.jpg")
        val boxMeshComp = TexturedMeshEntityComponent(cubeMesh, boxTexture)
        val box = Entity3D(
            Vector3f(5f, 0.0f, 5f),
            Vector3f(0f),
            Vector3f(1f, 1f, 1f)).apply {
            addComponent(boxMeshComp)
        }
        engine.addEntity(box)

        val wallTexture = Texture("/texture/wall.jpg")
        val wallMeshComp = TexturedMeshEntityComponent(cubeMesh, wallTexture)
        for(z in 0 until 10) {
            val wall = Entity3D(
                Vector3f(4.9f, 0.0f, z * 1.0f),
                Vector3f(0f),
                Vector3f(0.1f, 3f, 1f)).apply {
                addComponent(wallMeshComp)
            }
            engine.addEntity(wall)
        }

        val camera = Camera(
            Vector3f(0.0f, 1.0f, 10.0f),
            Vector3f(0.0f, -0.5f, -0.5f),
            Vector3f(0.0f, 1.0f, 0.0f)).apply {
            addComponent(TranslateCameraComponent(5.0f))
            addComponent(ZoomCameraComponent(15.0f, 2.0f, 15.0f))
            addComponent(RotateCameraComponent(1.0f,-0.85f, -0.25f))
        }
        engine.addEntity(camera)

        val sun = Light(
            Vector3f(0.0f, 10.0f, 0.0f),
            Vector4f(0.0f, 1.0f, 0.0f, 1.0f)).apply {
            addComponent(DirectionalLightComponent())
            addComponent(LightRotateLightComponent(0.5f))
            addComponent(ColorLightComponent())
        }
        engine.addEntity(sun)

    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {}
    override fun onFrame() {}
    override fun onCleanUp() {}
}
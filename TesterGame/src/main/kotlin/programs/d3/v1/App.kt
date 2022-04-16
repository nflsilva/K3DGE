package programs.d3.v1

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.camera.Camera
import k3dge.core.camera.component.RotateCameraComponent
import k3dge.core.camera.component.TranslateCameraComponent
import k3dge.core.camera.component.ZoomCameraComponent
import k3dge.core.entity.Entity
import k3dge.core.entity.component3d.TexturedMeshEntityComponent
import k3dge.core.light.Light
import k3dge.core.light.component.ColorLightComponent
import k3dge.core.light.component.DirectionalLightComponent
import k3dge.core.light.component.LightRotateLightComponent
import k3dge.render.common.model.Mesh
import k3dge.render.common.model.Texture
import k3dge.tools.ResourceManager
import k3dge.ui.dto.InputStateData
import org.joml.Vector3f
import org.joml.Vector4f

val engine = CoreEngine()

fun main(args: Array<String>) {

    val gameLogic = GameLogic()
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class GameLogic : CoreEngineDelegate {

    override fun onStart() {

        val terrainMesh = Mesh(Mesh.Dimensions.D3, Mesh.Usage.STATIC, "/mesh/terrain.obj")
        val terrainTexture = Texture("/texture/lowPolyAtlas.png")
        val terrainMeshComp = TexturedMeshEntityComponent(terrainMesh, terrainTexture)
        val terrain = Entity(
            Vector3f(0f),
            Vector3f(0f),
            Vector3f(1f, 1f, 1f)).apply {
            addComponent(terrainMeshComp)
        }
        engine.addEntity(terrain)

        val cubeMesh = Mesh(Mesh.Dimensions.D3, Mesh.Usage.STATIC, "/mesh/cube.obj")
        val boxTexture = Texture("/texture/box.jpg")
        val boxMeshComp = TexturedMeshEntityComponent(cubeMesh, boxTexture)
        val box = Entity(
            Vector3f(5f, 0.0f, 5f),
            Vector3f(0f),
            Vector3f(1f, 1f, 1f)).apply {
            addComponent(boxMeshComp)
        }
        engine.addEntity(box)

        val wallTexture = Texture("/texture/wall.jpg")
        val wallMeshComp = TexturedMeshEntityComponent(cubeMesh, wallTexture)
        for(z in 0 until 10) {
            val wall = Entity(
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
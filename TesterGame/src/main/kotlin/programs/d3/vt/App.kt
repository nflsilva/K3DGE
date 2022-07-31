package programs.d3.vt

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.camera.Camera
import k3dge.core.camera.component.RotateCameraComponent
import k3dge.core.camera.component.TranslateCameraComponent
import k3dge.core.camera.component.ZoomCameraComponent
import k3dge.core.entity.Entity3D
import k3dge.core.entity.component3d.TexturedMeshEntityComponent
import k3dge.core.light.Light
import k3dge.core.light.component.ColorLightComponent
import k3dge.core.light.component.DirectionalLightComponent
import k3dge.core.light.component.LightRotateLightComponent
import k3dge.render.common.enum.MeshDimensions
import k3dge.render.common.enum.MeshUsage
import k3dge.render.common.model.Mesh
import k3dge.render.common.model.Texture
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

        val terrainMesh = Mesh(MeshDimensions.D3, MeshUsage.STATIC, "/mesh/terrain.obj")
        val terrainTexture = Texture("/texture/lowPolyAtlas.png")
        val terrainMeshComp = TexturedMeshEntityComponent(terrainMesh, terrainTexture)
        val terrain = Entity3D(
            Vector3f(0f, 0f, 0f),
            Vector3f(0f, 0f, 0f),
            Vector3f(1f, 1f, 1f)).apply {
            addComponent(terrainMeshComp)
        }
        engine.addEntity(terrain)

        val houseMesh = Mesh(MeshDimensions.D3, MeshUsage.STATIC, "/mesh/home.obj")
        val houseMeshComp = TexturedMeshEntityComponent(houseMesh, terrainTexture)
        val box = Entity3D(
            Vector3f(0f, 0f, 0f),
            Vector3f(0f, 0f, 0f),
            Vector3f(0.1f, 0.1f, 0.1f)).apply {
            addComponent(houseMeshComp)
        }
        engine.addEntity(box)

        val camera = Camera(
            Vector3f(0.0f, 1.0f, 10.0f),
            Vector3f(0.0f, -0.5f, -0.5f),
            Vector3f(0.0f, 1.0f, 0.0f)).apply {
            addComponent(TranslateCameraComponent(5.0f))
            addComponent(ZoomCameraComponent(50.0f, 2.0f, 105.0f))
            addComponent(RotateCameraComponent(1.0f,-0.85f, -0.25f))
        }
        engine.addEntity(camera)

        val sun = Light(
            Vector3f(0.5f, -0.5f, 0.0f),
            Vector4f(0.0f, 1.0f, 0.0f, 1.0f)).apply {
            addComponent(DirectionalLightComponent())
            addComponent(LightRotateLightComponent(0.25f))
            addComponent(ColorLightComponent())
        }
        engine.addEntity(sun)

    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {}
    override fun onFrame() {}
    override fun onCleanUp() {}
}
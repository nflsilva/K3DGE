package programs.v1

import common.shader.StaticShader
import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.camera.Camera
import k3dge.core.camera.component.RotateCameraComponent
import k3dge.core.camera.component.TranslateCameraComponent
import k3dge.core.camera.component.ZoomCameraComponent
import k3dge.core.entity.Entity
import k3dge.core.entity.component.TexturedMeshEntityComponent
import k3dge.core.light.Light
import k3dge.core.light.component.ColorLightComponent
import k3dge.core.light.component.DirectionalLightComponent
import k3dge.core.light.component.LightRotateLightComponent
import k3dge.render.model.Mesh3DModel
import k3dge.render.model.TextureModel
import k3dge.tools.ResourceLoader
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

        val shader = StaticShader()
        val terrainMeshData = ResourceLoader.loadMeshFromFile("/mesh/terrain.obj")!!
        val terrainMesh = Mesh3DModel(
            terrainMeshData.vertices.toTypedArray(),
            terrainMeshData.textureCoordinates.toTypedArray(),
            terrainMeshData.normals.toTypedArray(),
            terrainMeshData.indices.toTypedArray())

        val cubeMeshData = ResourceLoader.loadMeshFromFile("/mesh/cube.obj")!!
        val cubeMesh = Mesh3DModel(
            cubeMeshData.vertices.toTypedArray(),
            cubeMeshData.textureCoordinates.toTypedArray(),
            cubeMeshData.normals.toTypedArray(),
            cubeMeshData.indices.toTypedArray())

        val terrainTextureData = ResourceLoader.loadTextureFromFile("/texture/lowPolyAtlas.png")!!
        val terrainTexture = TextureModel(terrainTextureData.width, terrainTextureData.height, terrainTextureData.data)
        val wallTextureData = ResourceLoader.loadTextureFromFile("/texture/wall.jpg")!!
        val wallTexture = TextureModel(wallTextureData.width, wallTextureData.height, wallTextureData.data)
        val boxTextureData = ResourceLoader.loadTextureFromFile("/texture/box.jpg")!!
        val boxTexture = TextureModel(boxTextureData.width, boxTextureData.height, boxTextureData.data)

        val terrainMeshComp = TexturedMeshEntityComponent(terrainMesh, terrainTexture, shader)
        val terrain = Entity(
            Vector3f(0f, 0f, 0f),
            Vector3f(0f, 0f, 0f),
            Vector3f(1f, 1f, 1f))
        terrain.addComponent(terrainMeshComp)
        engine.addEntity(terrain)

        val boxMeshComp = TexturedMeshEntityComponent(cubeMesh, boxTexture, shader)
        val box = Entity(
            Vector3f(5f, 0.0f, 5f),
            Vector3f(0f, 0f, 0f),
            Vector3f(1f, 1f, 1f))
        box.addComponent(boxMeshComp)
        engine.addEntity(box)

        val wallMeshComp = TexturedMeshEntityComponent(cubeMesh, wallTexture, shader)
        for(z in 0 until 10) {
            val wall = Entity(
                Vector3f(4.9f, 0.0f, z * 1.0f),
                Vector3f(0f, 0f, 0f),
                Vector3f(0.1f, 3f, 1f))
            wall.addComponent(wallMeshComp)
            engine.addEntity(wall)
        }

        val camera = Camera(
            Vector3f(0.0f, 1.0f, 10.0f),
            Vector3f(0.0f, -0.5f, -0.5f),
            Vector3f(0.0f, 1.0f, 0.0f))
        camera.addComponent(TranslateCameraComponent(5.0f))
        camera.addComponent(ZoomCameraComponent(15.0f, 2.0f, 15.0f))
        camera.addComponent(RotateCameraComponent(1.0f,-0.85f, -0.25f))
        engine.addEntity(camera)

        val sun = Light(
            Vector3f(0.0f, 10.0f, 0.0f),
            Vector4f(0.0f, 1.0f, 0.0f, 1.0f)
        )
        sun.addComponent(DirectionalLightComponent())
        sun.addComponent(LightRotateLightComponent(0.5f))
        sun.addComponent(ColorLightComponent())
        engine.addEntity(sun)

    }
    override fun onUpdate() {

    }
    override fun onFrame(elapsedTime: Double, input: InputStateData) {

    }
    override fun onCleanUp() {

    }

}
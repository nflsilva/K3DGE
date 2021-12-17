package terrain

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.camera.GameCamera
import k3dge.core.camera.component.RotateComponent
import k3dge.core.camera.component.TranslateComponent
import k3dge.core.camera.component.ZoomComponent
import k3dge.core.entity.GameEntity
import k3dge.core.entity.component.TexturedMeshComponent
import k3dge.render.model.MeshModel
import k3dge.render.model.ShaderModel
import k3dge.render.model.TextureModel
import k3dge.tools.ResourceLoader
import k3dge.ui.dto.InputStateData
import org.joml.Vector3f

val engine = CoreEngine()

fun main(args: Array<String>) {

    val gameLogic = GameLogic()
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class GameLogic : CoreEngineDelegate {

    override fun onStart() {

        val vertexSource = ResourceLoader
            .loadShaderSourceFromFile("/shader/static/vertex.glsl")
        val fragmentSource = ResourceLoader
            .loadShaderSourceFromFile("/shader/static/fragment.glsl")
        val shader = ShaderModel(vertexSource!!, fragmentSource!!)
        shader.addUniform("in_lightDirection")
        shader.addUniform("in_lightColor")

        val terrainMeshData = ResourceLoader.loadMeshFromFile("/mesh/terrain.obj")!!
        val terrainMesh = MeshModel(
            terrainMeshData.vertices.toTypedArray(),
            terrainMeshData.textureCoordinates.toTypedArray(),
            terrainMeshData.normals.toTypedArray(),
            terrainMeshData.indices.toTypedArray())

        val cubeMeshData = ResourceLoader.loadMeshFromFile("/mesh/cube.obj")!!
        val cubeMesh = MeshModel(
            cubeMeshData.vertices.toTypedArray(),
            cubeMeshData.textureCoordinates.toTypedArray(),
            cubeMeshData.normals.toTypedArray(),
            cubeMeshData.indices.toTypedArray())

        val terrainTextureData = ResourceLoader.loadTextureFromFile("/texture/grass.png")!!
        val terrainTexture = TextureModel(terrainTextureData.width, terrainTextureData.height, terrainTextureData.data)
        val wallTextureData = ResourceLoader.loadTextureFromFile("/texture/wall.jpg")!!
        val wallTexture = TextureModel(wallTextureData.width, wallTextureData.height, wallTextureData.data)
        val boxTextureData = ResourceLoader.loadTextureFromFile("/texture/box.jpg")!!
        val boxTexture = TextureModel(boxTextureData.width, boxTextureData.height, boxTextureData.data)

        val terrainMeshComp = TexturedMeshComponent(terrainMesh, terrainTexture)
        val terrain = GameEntity(
            Vector3f(0f, 0f, 0f),
            Vector3f(0f, 0f, 0f),
            Vector3f(1f, 1f, 1f),
            shader)
        terrain.addComponent(terrainMeshComp)
        engine.addEntity(terrain)

        val boxMeshComp = TexturedMeshComponent(cubeMesh, boxTexture)
        val box = GameEntity(
            Vector3f(5f, 0.0f, 5f),
            Vector3f(0f, 0f, 0f),
            Vector3f(1f, 1f, 1f),
            shader)
        box.addComponent(boxMeshComp)
        engine.addEntity(box)

        val wallMeshComp = TexturedMeshComponent(cubeMesh, wallTexture)
        for(z in 0 until 10) {
            val wall = GameEntity(
                Vector3f(0.0f, 0.0f, z * 1.0f),
                Vector3f(0f, 0f, 0f),
                Vector3f(0.1f, 3f, 1f),
                shader)
            wall.addComponent(wallMeshComp)
            engine.addEntity(wall)
        }

        val camera = GameCamera(
            Vector3f(0.0f, 1.0f, 10.0f),
            Vector3f(0.0f, -0.5f, -0.5f),
            Vector3f(0.0f, 1.0f, 0.0f))
        camera.addComponent(TranslateComponent(5.0f))
        camera.addComponent(ZoomComponent(15.0f, 2.0f, 15.0f))
        camera.addComponent(RotateComponent(1.0f,-0.85f, -0.25f))
        engine.addEntity(camera)

    }
    override fun onUpdate() {

    }
    override fun onFrame(elapsedTime: Double, input: InputStateData) {

    }
    override fun onCleanUp() {

    }

}
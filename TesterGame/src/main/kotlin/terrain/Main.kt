package terrain

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.component.TexturedMeshComponent
import k3dge.core.entity.GameEntity
import k3dge.render.model.MeshModel
import k3dge.render.model.ShaderModel
import k3dge.render.model.TextureModel
import k3dge.tools.ResourceLoader
import k3dge.ui.dto.InputState
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

        val terrainMeshData = ResourceLoader.loadMeshFromFile("/mesh/terrain.obj")!!
        val terrainMesh = MeshModel(
            terrainMeshData.vertices.toTypedArray(),
            terrainMeshData.textureCoordinates.toTypedArray(),
            terrainMeshData.normals.toTypedArray(),
            terrainMeshData.indices.toTypedArray())

        val terrainTextureData = ResourceLoader.loadTextureFromFile("/texture/cube.png")!!
        val terrainTexture = TextureModel(terrainTextureData.width, terrainTextureData.height, terrainTextureData.data)

        val terrainMeshComp = TexturedMeshComponent(terrainMesh, terrainTexture)
        val terrain = GameEntity(
            Vector3f(0f, 0f, -2f),
            Vector3f(0f, 0f, 0f),
            Vector3f(1f, 1f, 1f),
            shader)
        terrain.addComponent(terrainMeshComp)
        engine.addGameObject(terrain)

        val camera = Camera(
            Vector3f(0.0f, 0.0f, 0.0f),
            Vector3f(0.0f, -0.5f, -0.5f),
            Vector3f(0.0f, 0.5f, -0.5f))
        engine.addCamera(camera)

    }
    override fun onUpdate() {

    }
    override fun onFrame(elapsedTime: Double, input: InputState) {

    }
    override fun onCleanUp() {

    }

}
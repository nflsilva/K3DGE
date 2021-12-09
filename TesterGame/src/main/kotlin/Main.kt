import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.GameEntity
import k3dge.core.component.TexturedMeshComponent
import k3dge.render.model.MeshModel
import k3dge.render.model.ShaderModel
import k3dge.render.model.TextureModel
import k3dge.tools.ResourceLoader
import k3dge.ui.InputState
import org.joml.Vector3f

fun main(args: Array<String>) {

    val gameLogic = GameLogic()
    val engine = CoreEngine()
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

        val meshData = ResourceLoader
            .MeshData(
                vertices = listOf(
                    -0.5f, 0.5f, 0.0f,
                    -0.5f, -0.5f, 0.0f,
                    0.5f, -0.5f, 0.0f,
                    0.5f, 0.5f, 0.0f),
                textureCoordinates = listOf(
                    0.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f),
                normals = listOf(
                    0.0f, 0.0f, -1.0f,
                    0.0f, 0.0f, -1.0f,
                    0.0f, 0.0f, -1.0f
                ),
                indices = listOf(
                    0, 1, 3,
                    3, 1, 2))
        val mesh = MeshModel(
            meshData.vertices.toTypedArray(),
            meshData.textureCoordinates.toTypedArray(),
            meshData.normals.toTypedArray(),
            meshData.indices.toTypedArray())

        val textureData = ResourceLoader
            .loadTextureFromFile("/texture/debugTexture.png")
        val texture = TextureModel(textureData!!.width, textureData.height, textureData.data)

        val textMeshComponent = TexturedMeshComponent(mesh, texture)

        val cube = GameEntity(
            Vector3f(0f, 0f, 0f),
            Vector3f(0f, 0f, 0f),
            Vector3f(1f, 1f, 1f),
            shader)

        cube.addComponent(textMeshComponent)
    }

    override fun onUpdate() {

    }

    override fun onFrame(elapsedTime: Double, input: InputState) {

    }

    override fun onCleanUp() {

    }

}
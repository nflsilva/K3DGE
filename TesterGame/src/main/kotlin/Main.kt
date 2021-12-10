import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.entity.GameEntity
import k3dge.core.component.AutoSpinComponent
import k3dge.core.component.TexturedMeshComponent
import k3dge.render.model.MeshModel
import k3dge.render.model.ShaderModel
import k3dge.render.model.TextureModel
import k3dge.tools.ResourceLoader
import k3dge.ui.InputState
import org.joml.Random
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

        val meshData = ResourceLoader.loadMeshFromFile("/mesh/teddy.obj")!!
        val mesh = MeshModel(
            meshData.vertices.toTypedArray(),
            meshData.textureCoordinates.toTypedArray(),
            meshData.normals.toTypedArray(),
            meshData.indices.toTypedArray())

        val textureData = ResourceLoader.loadTextureFromFile("/texture/bear.jpg")!!
        val texture = TextureModel(textureData.width, textureData.height, textureData.data)

        val cubeMeshComp = TexturedMeshComponent(mesh, texture)
        val spinComp = AutoSpinComponent(0.5F)

        val r = Random()
        for(i in 0 until 1000) {
            val teddy = GameEntity(
                Vector3f((r.nextFloat() * 2 - 1) * 10, (r.nextFloat() * 2 - 1) * 10, -(r.nextFloat() * 10)),
                Vector3f(r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1),
                Vector3f(0.025f, 0.025f, 0.025f),
                shader)
            teddy.addComponent(cubeMeshComp)
            teddy.addComponent(spinComp)
            engine.addGameObject(teddy)
        }

    }

    override fun onUpdate() {

    }

    override fun onFrame(elapsedTime: Double, input: InputState) {

    }

    override fun onCleanUp() {

    }

}
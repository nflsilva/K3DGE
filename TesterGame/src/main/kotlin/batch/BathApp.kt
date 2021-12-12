package batch

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.camera.GameCamera
import k3dge.core.camera.component.RotateComponent
import k3dge.core.camera.component.TranslateComponent
import k3dge.core.camera.component.ZoomComponent
import k3dge.core.entity.GameEntity
import k3dge.core.entity.component.SpinComponent
import k3dge.core.entity.component.TexturedMeshComponent
import k3dge.render.model.MeshModel
import k3dge.render.model.ShaderModel
import k3dge.render.model.TextureModel
import k3dge.tools.ResourceLoader
import k3dge.ui.dto.InputStateData
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

        val cubeMeshData = ResourceLoader.loadMeshFromFile("/mesh/cube.obj")!!
        val cubeMesh = MeshModel(
            cubeMeshData.vertices.toTypedArray(),
            cubeMeshData.textureCoordinates.toTypedArray(),
            cubeMeshData.normals.toTypedArray(),
            cubeMeshData.indices.toTypedArray())

        val teddyMeshData = ResourceLoader.loadMeshFromFile("/mesh/teddy.obj")!!
        val teddyMesh = MeshModel(
            teddyMeshData.vertices.toTypedArray(),
            teddyMeshData.textureCoordinates.toTypedArray(),
            teddyMeshData.normals.toTypedArray(),
            teddyMeshData.indices.toTypedArray())

        val teddyTextureData = ResourceLoader.loadTextureFromFile("/texture/bear.jpg")!!
        val teddyTexture = TextureModel(teddyTextureData.width, teddyTextureData.height, teddyTextureData.data)

        val cubeTextureData = ResourceLoader.loadTextureFromFile("/texture/cube.png")!!
        val cubeTexture = TextureModel(cubeTextureData.width, cubeTextureData.height, cubeTextureData.data)

        val cubeMeshComp = TexturedMeshComponent(cubeMesh, cubeTexture)
        val teddyMeshComp = TexturedMeshComponent(teddyMesh, teddyTexture)
        val spinComp = SpinComponent(0.5F)

        val r = Random()
        for(i in 0 until 100) {
            val cube = GameEntity(
                Vector3f((r.nextFloat() * 2 - 1) * 10, (r.nextFloat() * 2 - 1) * 10, -(r.nextFloat() * 10)),
                Vector3f(r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1),
                Vector3f(1f, 1f, 1f),
                shader)
            cube.addComponent(cubeMeshComp)
            cube.addComponent(spinComp)
            engine.addGameObject(cube)

            val teddy = GameEntity(
                Vector3f((r.nextFloat() * 2 - 1) * 10, (r.nextFloat() * 2 - 1) * 10, -(r.nextFloat() * 10)),
                Vector3f(r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1),
                Vector3f(0.05f, 0.05f, 0.05f),
                shader)
            teddy.addComponent(teddyMeshComp)
            teddy.addComponent(spinComp)
            engine.addGameObject(teddy)
        }

        val camera = GameCamera(
            Vector3f(0.0f, 0.0f, 0.0f),
            Vector3f(0.0f, 0.0f, -1.0f),
            Vector3f(0.0f, 1.0f, 0.0f))
        camera.addComponent(TranslateComponent(5.0f))
        camera.addComponent(ZoomComponent(15.0f))
        camera.addComponent(RotateComponent(5.0f,0.85f, 0.25f))
        engine.addCamera(camera)

    }
    override fun onUpdate() {

    }
    override fun onFrame(elapsedTime: Double, input: InputStateData) {

    }
    override fun onCleanUp() {

    }

}
package programs.d3.v0

import k3dge.render.renderer3d.shader.StaticShader
import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.camera.Camera
import k3dge.core.camera.component.RotateCameraComponent
import k3dge.core.camera.component.TranslateCameraComponent
import k3dge.core.camera.component.ZoomCameraComponent
import k3dge.core.entity.Entity
import k3dge.core.entity.component.SpinEntityComponent
import k3dge.core.entity.component3d.TexturedMeshEntityComponent
import k3dge.render.renderer3d.entity.StaticObjectModel
import k3dge.render.common.model.Texture
import k3dge.tools.ResourceManager
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

        val shader = StaticShader()

        val cubeMeshData = ResourceManager.loadMeshFromFile("/mesh/cube.obj")!!
        val cubeMesh = StaticObjectModel(
            cubeMeshData.vertices.toTypedArray(),
            cubeMeshData.textureCoordinates.toTypedArray(),
            cubeMeshData.normals.toTypedArray(),
            cubeMeshData.indices.toTypedArray())

        val teddyMeshData = ResourceManager.loadMeshFromFile("/mesh/teddy.obj")!!
        val teddyMesh = StaticObjectModel(
            teddyMeshData.vertices.toTypedArray(),
            teddyMeshData.textureCoordinates.toTypedArray(),
            teddyMeshData.normals.toTypedArray(),
            teddyMeshData.indices.toTypedArray())

        val teddyTextureData = ResourceManager.loadTextureFromFile("/texture/bear.jpg")!!
        val teddyTexture = Texture(teddyTextureData.width, teddyTextureData.height, teddyTextureData.data)

        val cubeTextureData = ResourceManager.loadTextureFromFile("/texture/cube.png")!!
        val cubeTexture = Texture(cubeTextureData.width, cubeTextureData.height, cubeTextureData.data)

        val cubeMeshComp = TexturedMeshEntityComponent(cubeMesh, cubeTexture, shader)
        val teddyMeshComp = TexturedMeshEntityComponent(teddyMesh, teddyTexture, shader)
        val spinComp = SpinEntityComponent(0.5F)

        val r = Random()
        for(i in 0 until 100) {
            val cube = Entity(
                Vector3f((r.nextFloat() * 2 - 1) * 10, (r.nextFloat() * 2 - 1) * 10, -(r.nextFloat() * 10)),
                Vector3f(r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1),
                Vector3f(1f, 1f, 1f))
            cube.addComponent(cubeMeshComp)
            cube.addComponent(spinComp)
            engine.addEntity(cube)

            val teddy = Entity(
                Vector3f((r.nextFloat() * 2 - 1) * 10, (r.nextFloat() * 2 - 1) * 10, -(r.nextFloat() * 10)),
                Vector3f(r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1),
                Vector3f(0.05f, 0.05f, 0.05f))
            teddy.addComponent(teddyMeshComp)
            teddy.addComponent(spinComp)
            engine.addEntity(teddy)
        }

        val camera = Camera(
            Vector3f(0.0f, 0.0f, 0.0f),
            Vector3f(0.0f, -0.25f, -1.0f),
            Vector3f(0.0f, 1.0f, 0.0f))
        camera.addComponent(TranslateCameraComponent(5.0f))
        camera.addComponent(ZoomCameraComponent(1.0f))
        camera.addComponent(RotateCameraComponent(1.0f,0.85f, 0.25f))
        engine.addEntity(camera)

    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {

    }
    override fun onFrame() {

    }
    override fun onCleanUp() {

    }

}
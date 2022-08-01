package programs.d3.v0

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.camera.Camera
import k3dge.core.camera.component.RotateCameraComponent
import k3dge.core.camera.component.TranslateCameraComponent
import k3dge.core.camera.component.ZoomCameraComponent
import k3dge.core.entity.Entity3D
import k3dge.core.entity.component.SpinEntityComponent
import k3dge.core.entity.component3d.TexturedMeshEntityComponent
import k3dge.render.common.enum.MeshDimensions
import k3dge.render.common.enum.MeshUsage
import k3dge.render.common.model.Mesh
import k3dge.render.common.model.Texture
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

        val cubeMesh = Mesh(MeshDimensions.D3, MeshUsage.STATIC, "/mesh/cube.obj")
        val cubeTexture = Texture("/texture/cube.png")
        val cubeMeshComp = TexturedMeshEntityComponent(cubeMesh, cubeTexture)

        val teddyTexture = Texture("/texture/bear.jpg")
        val teddyMesh = Mesh(MeshDimensions.D3, MeshUsage.STATIC, "/mesh/teddy.obj")
        val teddyMeshComp = TexturedMeshEntityComponent(teddyMesh, teddyTexture)

        val spinComp = SpinEntityComponent(0.5F)

        val r = Random()
        for(i in 0 until 100) {
            val cube = Entity3D(
                Vector3f((r.nextFloat() * 2 - 1) * 10, (r.nextFloat() * 2 - 1) * 10, -(r.nextFloat() * 10)),
                Vector3f(r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1),
                Vector3f(1f, 1f, 1f))
            cube.addComponent(cubeMeshComp)
            cube.addComponent(spinComp)
            engine.addEntity(cube)

            val teddy = Entity3D(
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
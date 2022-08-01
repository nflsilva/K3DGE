package programs.d3.v2

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.camera.Camera
import k3dge.core.camera.component.RotateCameraComponent
import k3dge.core.camera.component.TranslateCameraComponent
import k3dge.core.camera.component.ZoomCameraComponent
import k3dge.core.entity.Entity2D
import k3dge.core.entity.Entity3D
import k3dge.core.entity.component2d.GuiComponent
import k3dge.core.entity.component3d.TexturedMeshEntityComponent
import k3dge.core.light.Light
import k3dge.core.light.component.ColorLightComponent
import k3dge.core.light.component.DirectionalLightComponent
import k3dge.core.light.component.LightRotateLightComponent
import k3dge.render.common.enum.MeshDimensions
import k3dge.render.common.enum.MeshUsage
import k3dge.render.common.model.Mesh
import k3dge.render.common.model.Texture
import k3dge.tools.ResourceManager
import k3dge.ui.dto.InputStateData
import org.joml.Random
import org.joml.Vector2f
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

        val terrainMesh = Mesh(MeshDimensions.D3, MeshUsage.STATIC, "/mesh/terrainRandomElevation30.obj")
        val pineTreeMesh = Mesh(MeshDimensions.D3, MeshUsage.STATIC, "/mesh/pineTree.obj")

        val lowPolyAtlasTextureData = ResourceManager.loadTextureFromFile("/texture/lowPolyAtlas.png")
        val lowPolyAtlasTexture = Texture(lowPolyAtlasTextureData.width, lowPolyAtlasTextureData.height, lowPolyAtlasTextureData.data)
        val testTextureData = ResourceManager.loadTextureFromFile("/texture/cube.png")
        val testTexture = Texture(testTextureData.width, testTextureData.height, testTextureData.data)

        val r = Random()
        val pineTreeMeshComp = TexturedMeshEntityComponent(pineTreeMesh, lowPolyAtlasTexture)
        for(x in 1 until 30) {
            for(y in 1 until 30)
            {
                if(r.nextInt(5) == 0){
                    val pineTree = Entity3D(
                        Vector3f( x.toFloat(), 0f, y.toFloat()),
                        Vector3f(0f, 0f, 0f),
                        Vector3f(0.5f, 0.5f, 0.5f)).apply {
                        addComponent(pineTreeMeshComp)
                    }
                    engine.addEntity(pineTree)
                }
            }
        }

        val terrainMeshComp = TexturedMeshEntityComponent(terrainMesh, lowPolyAtlasTexture)
        val terrain = Entity3D(Vector3f(0f, 0f, 0f), Vector3f(0f, 0f, 0f), Vector3f(1f, 1f, 1f))
        terrain.addComponent(terrainMeshComp)
        engine.addEntity(terrain)

        val guiComp = GuiComponent(testTexture)
        val gui = Entity2D(
            Vector2f(10.5f, 10.5f),
            0.0f,
            Vector2f(5.0f, 5.0f)).apply {
            addComponent(guiComp)
        }
        engine.addEntity(gui)

        val camera = Camera(
            Vector3f(5.0f, 5.0f, 0.0f),
            Vector3f(0f, -0.25f, 0.5f),
            Vector3f(0.0f, 1.0f, 0.0f)).apply {
            addComponent(TranslateCameraComponent(5.0f))
            addComponent(ZoomCameraComponent(15.0f, 2.0f, 20.0f))
            addComponent(RotateCameraComponent(1.0f,-0.85f, -0.25f))
        }
        engine.addEntity(camera)

        val sun = Light(
            Vector3f(-1.0f, 0.0f, 0.0f),
            Vector4f(1.0f, 1.0f, 1.0f, 1.0f)).apply {
            addComponent(DirectionalLightComponent())
            addComponent(LightRotateLightComponent(0.10f))
            addComponent(ColorLightComponent())
        }
        engine.addEntity(sun)

    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {}
    override fun onFrame() {}
    override fun onCleanUp() {}
}
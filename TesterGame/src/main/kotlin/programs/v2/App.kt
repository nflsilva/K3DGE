package programs.v2

import common.shader.GuiShader
import common.shader.StaticShader
import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.camera.Camera
import k3dge.core.camera.component.RotateCameraComponent
import k3dge.core.camera.component.TranslateCameraComponent
import k3dge.core.camera.component.ZoomCameraComponent
import k3dge.core.entity.Entity
import k3dge.core.entity.component.GuiEntityComponent
import k3dge.core.entity.component.TexturedMeshEntityComponent
import k3dge.core.light.Light
import k3dge.core.light.component.ColorLightComponent
import k3dge.core.light.component.DirectionalLightComponent
import k3dge.core.light.component.LightRotateLightComponent
import k3dge.render.model.Mesh3DModel
import k3dge.render.model.TextureModel
import k3dge.tools.ResourceLoader
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

        val staticShader = StaticShader()
        val guiShader = GuiShader()

        val terrainMeshData = ResourceLoader.loadMeshFromFile("/mesh/terrainRandomElevation.obj")!!
        val terrainMesh = Mesh3DModel(
            terrainMeshData.vertices.toTypedArray(),
            terrainMeshData.textureCoordinates.toTypedArray(),
            terrainMeshData.normals.toTypedArray(),
            terrainMeshData.indices.toTypedArray())
        val pineTreeData = ResourceLoader.loadMeshFromFile("/mesh/pineTree.obj")!!
        val pineTreeMesh = Mesh3DModel(
            pineTreeData.vertices.toTypedArray(),
            pineTreeData.textureCoordinates.toTypedArray(),
            pineTreeData.normals.toTypedArray(),
            pineTreeData.indices.toTypedArray())

        val lowPolyAtlasTextureData = ResourceLoader.loadTextureFromFile("/texture/lowPolyAtlas.png")!!
        val lowPolyAtlasTexture = TextureModel(lowPolyAtlasTextureData.width, lowPolyAtlasTextureData.height, lowPolyAtlasTextureData.data)
        val testTextureData = ResourceLoader.loadTextureFromFile("/texture/cube.png")!!
        val testTexture = TextureModel(testTextureData.width, testTextureData.height, testTextureData.data)

        val r = Random()
        val pineTreeMeshComp = TexturedMeshEntityComponent(pineTreeMesh, lowPolyAtlasTexture, staticShader)
        for(x in 1 until 10) {
            for(y in 1 until 10)
            {
                if(r.nextInt(5) == 0){
                    val pineTree = Entity(
                        Vector3f( x.toFloat(), 0f, y.toFloat()),
                        Vector3f(0f, 0f, 0f),
                        Vector3f(0.5f, 0.5f, 0.5f)).apply {
                        addComponent(pineTreeMeshComp)
                    }
                    engine.addEntity(pineTree)
                }
            }

        }

        val terrainMeshComp = TexturedMeshEntityComponent(terrainMesh, lowPolyAtlasTexture, staticShader)
        val terrain = Entity(Vector3f(0f, 0f, 0f), Vector3f(0f, 0f, 0f), Vector3f(1f, 1f, 1f))
        terrain.addComponent(terrainMeshComp)
        engine.addEntity(terrain)

        val guiComp = GuiEntityComponent(testTexture, guiShader)
        val gui = Entity(
            Vector2f(0.5f, 0.5f),
            0.0f,
            Vector2f(0.25f, 0.25f)).apply {
            addComponent(guiComp)
        }
        //engine.addEntity(gui)

        val camera = Camera(Vector3f(5.0f, 5.0f, 10.0f),
            Vector3f(0f, -0.25f, -0.5f),
            Vector3f(0.0f, 1.0f, 0.0f)).apply {
            addComponent(TranslateCameraComponent(5.0f))
            addComponent(ZoomCameraComponent(15.0f, 2.0f, 20.0f))
            addComponent(RotateCameraComponent(1.0f,-0.85f, -0.25f))
        }
        engine.addEntity(camera)

        val sun = Light(
            Vector3f(-50.0f, -1.0f, 0.0f),
            Vector4f(1.0f, 1.0f, 1.0f, 1.0f)).apply {
            addComponent(DirectionalLightComponent())
            addComponent(LightRotateLightComponent(-0.025f))
            addComponent(ColorLightComponent())
        }
        engine.addEntity(sun)

    }
    override fun onUpdate() {}
    override fun onFrame(elapsedTime: Double, input: InputStateData) {}
    override fun onCleanUp() {}
}
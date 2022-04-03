package programs.d2.v0

import k3dge.configuration.EngineConfiguration
import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.entity.Entity
import k3dge.core.entity.component.MoveEntityComponent
import k3dge.core.entity.component2d.SpriteEntityComponent
import k3dge.render.common.model.TextureModel
import k3dge.tools.ResourceLoader
import k3dge.ui.dto.InputStateData
import org.joml.Vector2f


val engine = CoreEngine(EngineConfiguration.default().apply {
    is3D = false
})

fun main(args: Array<String>) {

    val gameLogic = GameLogic()
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class GameLogic : CoreEngineDelegate {

    override fun onStart() {

        val lowPolyAtlasTextureData = ResourceLoader.loadTextureFromFile("/texture/lowPolyAtlas.png")!!
        val lowPolyAtlasTexture = TextureModel(lowPolyAtlasTextureData.width, lowPolyAtlasTextureData.height, lowPolyAtlasTextureData.data)

        val testTextureData = ResourceLoader.loadTextureFromFile("/texture/cube.png")!!
        val testTexture = TextureModel(testTextureData.width, testTextureData.height, testTextureData.data)

        val textures = listOf(lowPolyAtlasTexture, testTexture)
        var i = 0

        for(x in 0 until 1000) {
            for(y in 0 until 100) {
                val sprite = Entity(Vector2f(x * 15F, y * 15F), 0.0f, Vector2f(1f, 1f))
                val ti = i++ % 2
                val text = textures[ti]
                if(ti > -1){
                    val subSpriteSize = 1.0F / 2.0F

                    val subSpriteV = 0.0F * subSpriteSize  //Vertical
                    val subSpriteU = 0.0F * subSpriteSize  //Horizontal

                    val texCoords = arrayOf(
                        Vector2f(subSpriteU, subSpriteV),
                        Vector2f(subSpriteU, subSpriteV + subSpriteSize),
                        Vector2f(subSpriteU + subSpriteSize, subSpriteV + subSpriteSize),
                        Vector2f(subSpriteU + subSpriteSize, subSpriteV))

                    sprite.addComponent(SpriteEntityComponent(text, texCoords))
                }
                else {
                    sprite.addComponent(SpriteEntityComponent(text))
                }
                sprite.addComponent(MoveEntityComponent())
                engine.addEntity(sprite)
            }
        }

    }
    override fun onUpdate() {

    }
    override fun onFrame(elapsedTime: Double, input: InputStateData) {

    }
    override fun onCleanUp() {

    }

}
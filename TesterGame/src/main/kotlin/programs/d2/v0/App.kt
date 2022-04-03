package programs.d2.v0

import k3dge.configuration.EngineConfiguration
import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.entity.Entity
import k3dge.core.entity.component.MoveEntityComponent
import k3dge.core.entity.component2d.SpriteAnimationComponent
import k3dge.core.entity.component2d.SpriteEntityComponent
import k3dge.render.common.model.TextureAtlas
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
        val lowPolyAtlasTexture = TextureAtlas(
            lowPolyAtlasTextureData.width,
            lowPolyAtlasTextureData.height,
            lowPolyAtlasTextureData.data,
            4, 4)
        lowPolyAtlasTexture.setSpriteCoordinates("purple", 3, 3)

        val testTextureData = ResourceLoader.loadTextureFromFile("/texture/cube.png")!!
        val testTexture = TextureModel(testTextureData.width, testTextureData.height, testTextureData.data)

        val textures = listOf(lowPolyAtlasTexture, testTexture)
        var i = 0

        for(x in 0 until 1000) {
            for(y in 0 until 100) {
                val sprite = Entity(Vector2f(x * 16F, y * 16F), 0.0f, Vector2f(1f, 1f))
                val ti = i++ % 2
                val text = textures[ti]
                if(ti == 1){
                    lowPolyAtlasTexture.getSpriteCoordinates("purple")?.let {
                        sprite.addComponent(SpriteEntityComponent(text, it))
                    }
                }
                else {
                    sprite.addComponent(SpriteEntityComponent(text))
                }
                sprite.addComponent(MoveEntityComponent())
                engine.addEntity(sprite)
            }
        }

        val selectAtlasTextureData = ResourceLoader.loadTextureFromFile("/texture/selectAtlas.png")!!
        val selectAtlasTexture = TextureAtlas(
            selectAtlasTextureData.width,
            selectAtlasTextureData.height,
            selectAtlasTextureData.data, 2, 2)

        selectAtlasTexture.setSpriteCoordinates("0", 0, 0)
        selectAtlasTexture.setSpriteCoordinates("1", 1, 0)
        selectAtlasTexture.setSpriteCoordinates("2", 1, 0)

        val animatedSprite = Entity(Vector2f(0F, 0F), 0.0f, Vector2f(1f, 1f))

        val keyframes = mutableListOf(
            SpriteAnimationComponent.SpriteKeyframe(
                selectAtlasTexture.id, selectAtlasTexture.getSpriteCoordinates("0")!!, 0.5),
            SpriteAnimationComponent.SpriteKeyframe(
                selectAtlasTexture.id, selectAtlasTexture.getSpriteCoordinates("1")!!, 0.5),
            SpriteAnimationComponent.SpriteKeyframe(
                selectAtlasTexture.id, selectAtlasTexture.getSpriteCoordinates("2")!!, 0.5),
            SpriteAnimationComponent.SpriteKeyframe(
                selectAtlasTexture.id, selectAtlasTexture.getSpriteCoordinates("1")!!, 0.5))

        animatedSprite.addComponent(SpriteAnimationComponent().apply {
            addStateKeyframes("default", keyframes)
            setState("default")
        })

        animatedSprite.addComponent(MoveEntityComponent())
        engine.addEntity(animatedSprite)

    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {

    }
    override fun onFrame() {

    }
    override fun onCleanUp() {

    }

}
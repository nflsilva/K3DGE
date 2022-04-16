package programs.d2.v0

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.ui.dto.InputStateData

val engine = CoreEngine()

fun main(args: Array<String>) {

    val gameLogic = GameLogic()
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class GameLogic : CoreEngineDelegate {

    override fun onStart() {

        /*
        val lowPolyAtlasData = ResourceManager.loadTextureFromFile("/texture/lowPolyAtlas.png")!!

        val lowPolyAtlas = SpriteAtlas(lowPolyAtlasData, 4, 4).apply {
            setSprite("purple", 3, 3, X4)
        }

        val testTextureData = ResourceManager.loadTextureFromFile("/texture/cube.png")!!
        val testSprite = Sprite(testTextureData, X16)

        var i = 0

        for(x in 0 until 1000) {
            for(y in 0 until 100) {
                val sprite = Entity(Vector2f(x * 16F, y * 16F), 0.0f, Vector2f(1f, 1f))
                val ti = i++ % 2

                if(ti == 1){
                    lowPolyAtlas.getSprite("purple")?.let {
                        sprite.addComponent(SpriteEntityComponent(it))
                    }
                }
                else {
                    sprite.addComponent(SpriteEntityComponent(testSprite))
                }
                sprite.addComponent(MoveEntityComponent())
                engine.addEntity(sprite)
            }
        }

        val selectAtlasTextureData = ResourceManager.loadTextureFromFile("/texture/selectAtlas.png")!!
        val selectAtlasTexture = SpriteAtlas(
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
        engine.addEntity(animatedSprite)*/

    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {

    }
    override fun onFrame() {

    }
    override fun onCleanUp() {

    }

}
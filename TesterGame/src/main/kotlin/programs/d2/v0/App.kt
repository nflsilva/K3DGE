package programs.d2.v0

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.entity.Entity
import k3dge.core.entity.component.MoveEntityComponent
import k3dge.core.entity.component2d.SpriteAnimationComponent
import k3dge.core.entity.component2d.SpriteEntityComponent
import k3dge.render.renderer2d.dto.Sprite
import k3dge.render.renderer2d.model.SpriteAtlas
import k3dge.render.renderer2d.model.SpriteSizeEnum
import k3dge.ui.dto.InputStateData
import org.joml.Vector2f

val engine = CoreEngine()

fun main(args: Array<String>) {

    val gameLogic = GameLogic()
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class GameLogic : CoreEngineDelegate {

    override fun onStart() {

        val lowPolyAtlas = SpriteAtlas("/texture/lowPolyAtlas.png", 4, 4).apply {
            setSprite(SpriteSizeEnum.X4,"purple", 3, 3)
        }

        val testSprite = Sprite(SpriteSizeEnum.X16,"/texture/cube.png",)
        val selectAtlasTexture = SpriteAtlas("/texture/selectAtlas.png", 2, 2).apply {
            setSprite(SpriteSizeEnum.X16,"0", 0, 0)
            setSprite(SpriteSizeEnum.X16,"1", 1, 0)
            setSprite(SpriteSizeEnum.X16,"2", 1, 0)
        }

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

        val animatedSprite = Entity(Vector2f(0F, 0F), 0.0f, Vector2f(1f, 1f))
        animatedSprite.addComponent(SpriteAnimationComponent().apply {
            addAnimationKeyframe("default", selectAtlasTexture.getSprite("0"), 0.5)
            addAnimationKeyframe("default", selectAtlasTexture.getSprite("1"), 0.5)
            addAnimationKeyframe("default", selectAtlasTexture.getSprite("2"), 0.5)
            addAnimationKeyframe("default", selectAtlasTexture.getSprite("1"), 0.5)
            setState("default")
        })

        animatedSprite.addComponent(MoveEntityComponent())
        engine.addEntity(animatedSprite)

    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {}
    override fun onFrame() {}
    override fun onCleanUp() {}

}
package programs.d2

import core.CoreEngine
import core.CoreEngineDelegate
import core.entity.Entity2D
import core.entity.component.EntityMoveComponent
import core.entity.component2d.SpriteComponent
import core.entity.component2d.SpriteRotationComponent
import org.joml.Vector2f
import render.renderer2d.dto.Sprite
import render.renderer2d.model.SpriteAtlas
import render.renderer2d.model.SpriteSizeEnum
import ui.dto.InputStateData

fun main(args: Array<String>) {

    val engine = CoreEngine()
    val gameLogic = SpritesBatchRendering(engine)
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class SpritesBatchRendering(private val engine: CoreEngine) : CoreEngineDelegate {

    override fun onStart() {

        val lowPolyAtlas = SpriteAtlas("/texture/lowPolyAtlas.png", 4, 4).apply {
            setSprite(SpriteSizeEnum.X4,"purple", 3, 3)
        }

        val testSprite = Sprite(SpriteSizeEnum.X16,"/texture/cube.png",)

        var i = 0
        for(x in 0 until 100) {
            for(y in 0 until 1000) {
                val sprite = Entity2D(Vector2f(x * 16F, y * 16F), 0.0f, Vector2f(1f, 1f))
                val ti = i++ % 2

                if(ti == 1){
                    sprite.addComponent(SpriteComponent(lowPolyAtlas.getSprite("purple")))
                }
                else {
                    sprite.addComponent(SpriteComponent(testSprite))
                }
                sprite.addComponent(EntityMoveComponent())
                sprite.addComponent(SpriteRotationComponent())
                engine.addEntity(sprite)
            }
        }
    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {}
    override fun onFrame() {}
    override fun onCleanUp() {}

}
package programs.d2.v1

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.entity.Entity
import k3dge.core.entity.component.MoveEntityComponent
import k3dge.core.entity.component2d.SpriteAnimationComponent
import k3dge.render.renderer2d.model.SpriteAtlas
import k3dge.render.renderer2d.model.SpriteSizeEnum
import k3dge.ui.dto.InputStateData
import org.joml.Vector2f

val engine = CoreEngine()
val animComp = SpriteAnimationComponent()

fun main(args: Array<String>) {

    val gameLogic = GameLogic()
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class GameLogic : CoreEngineDelegate {

    override fun onStart() {

        val animatedSprite = Entity(Vector2f(0F, 0F), 0.0f, Vector2f(1f, 1f))
        val atlas = SpriteAtlas("/texture/dungeon.png", 9, 28).apply {
            setSprite(SpriteSizeEnum.X16,"walking0", 6, 0)
            setSprite(SpriteSizeEnum.X16,"walking1", 6, 1)
            setSprite(SpriteSizeEnum.X16,"walking2", 6, 2)
            setSprite(SpriteSizeEnum.X16,"walking3", 6, 3)
            setSprite(SpriteSizeEnum.X16,"walking4", 6, 4)
            setSprite(SpriteSizeEnum.X16,"walking5", 6, 5)

            setSprite(SpriteSizeEnum.X16,"idle0", 5, 0)
            setSprite(SpriteSizeEnum.X16,"idle1", 5, 1)
            setSprite(SpriteSizeEnum.X16,"idle2", 5, 2)
            setSprite(SpriteSizeEnum.X16,"idle3", 5, 3)
            setSprite(SpriteSizeEnum.X16,"idle4", 5, 4)
            setSprite(SpriteSizeEnum.X16,"idle5", 5, 5)
        }

        animComp.apply {
            addAnimationKeyframe("walking", atlas.getSprite("walking0"), 0.05)
            addAnimationKeyframe("walking", atlas.getSprite("walking1"), 0.05)
            addAnimationKeyframe("walking", atlas.getSprite("walking2"), 0.05)
            addAnimationKeyframe("walking", atlas.getSprite("walking3"), 0.05)
            addAnimationKeyframe("walking", atlas.getSprite("walking4"), 0.05)
            addAnimationKeyframe("walking", atlas.getSprite("walking5"), 0.05)

            addAnimationKeyframe("idle", atlas.getSprite("idle0"), 0.05)
            addAnimationKeyframe("idle", atlas.getSprite("idle1"), 0.05)
            addAnimationKeyframe("idle", atlas.getSprite("idle2"), 0.05)
            addAnimationKeyframe("idle", atlas.getSprite("idle3"), 0.05)
            addAnimationKeyframe("idle", atlas.getSprite("idle4"), 0.05)
            addAnimationKeyframe("idle", atlas.getSprite("idle5"), 0.05)
            setState("idle")
        }

        animatedSprite.addComponent(animComp)
        animatedSprite.addComponent(MoveEntityComponent(50F))
        engine.addEntity(animatedSprite)

    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {
        if(input.isKeyPressed(InputStateData.KEY_D)){
            animComp.setState("walking")
        }
        else {
            animComp.setState("idle")
        }
    }
    override fun onFrame() {}
    override fun onCleanUp() {}

}
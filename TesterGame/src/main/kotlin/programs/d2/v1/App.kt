package programs.d2.v1

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.entity.component2d.SpriteAnimationComponent
import k3dge.ui.dto.InputStateData

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

        /*
        val animatedSprite = Entity(Vector2f(0F, 0F), 0.0f, Vector2f(1f, 1f))

        val atlasData = ResourceManager.loadTextureFromFile("/texture/dungeon.png")!!

        val atlas = SpriteAtlas(atlasData.width, atlasData.height, atlasData.data,
             9, 28).apply {
            setSpriteCoordinates("walking0", 6, 0)
            setSpriteCoordinates("walking1", 6, 1)
            setSpriteCoordinates("walking2", 6, 2)
            setSpriteCoordinates("walking3", 6, 3)
            setSpriteCoordinates("walking4", 6, 4)
            setSpriteCoordinates("walking5", 6, 5)

            setSpriteCoordinates("idle0", 5, 0)
            setSpriteCoordinates("idle1", 5, 1)
            setSpriteCoordinates("idle2", 5, 2)
            setSpriteCoordinates("idle3", 5, 3)
            setSpriteCoordinates("idle4", 5, 4)
            setSpriteCoordinates("idle5", 5, 5)
        }

        animComp.apply {
            addStateKeyframes("walking", mutableListOf(
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("walking0"), 0.05),
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("walking1"), 0.05),
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("walking2"), 0.05),
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("walking3"), 0.05),
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("walking4"), 0.05),
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("walking5"), 0.05),
            ))
            addStateKeyframes("idle", mutableListOf(
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("idle0"), 0.05),
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("idle1"), 0.05),
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("idle2"), 0.05),
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("idle3"), 0.05),
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("idle4"), 0.05),
                SpriteKeyframe(atlas.id, atlas.getSpriteCoordinates("idle5"), 0.05),
            ))
            setState("idle")
        }

        animatedSprite.addComponent(animComp)
        animatedSprite.addComponent(MoveEntityComponent(50F))
        engine.addEntity(animatedSprite)*/

    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {
        if(input.isKeyPressed(InputStateData.KEY_D)){
            animComp.setState("walking")
        }
        else {
            animComp.setState("idle")
        }
    }
    override fun onFrame() {

    }
    override fun onCleanUp() {

    }

}
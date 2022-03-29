package programs.d2.v0

import k3dge.configuration.EngineConfiguration
import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.entity.Entity
import k3dge.core.entity.component.MoveEntityComponent
import k3dge.core.entity.component.SpinEntityComponent
import k3dge.core.entity.component2d.SpriteEntityComponent
import k3dge.ui.dto.InputStateData
import org.joml.Vector2f
import org.joml.Vector3f


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

        for(x in 0 until 1000) {
            for(y in 0 until 100) {
                val sprite = Entity(Vector2f(x * 15F, y * 15F), 0.0f, Vector2f(1f, 1f))
                sprite.addComponent(SpriteEntityComponent())
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
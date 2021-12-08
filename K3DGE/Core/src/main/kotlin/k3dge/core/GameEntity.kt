package k3dge.core

import k3dge.render.RenderEngine
import k3dge.ui.InputState
import org.joml.Vector3f

abstract class GameEntity(
    val position: Vector3f,
    val rotation: Vector3f,
    val scale: Vector3f) {

    abstract fun onUpdate(elapsedTime: Double, input: InputState)
    abstract fun onFrame(graphics: RenderEngine)
    abstract fun cleanUp()
}
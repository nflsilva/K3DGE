package k3dge.core.entity

import k3dge.ui.InputState
import org.joml.Vector3f

open class GameCamera(
    var position: Vector3f,
    var forward: Vector3f,
    var up: Vector3f) {

    private var velocity: Vector3f = Vector3f(0.0f, 0.0f, 0.0f)

    init {
        position = position.normalize()
        forward = forward.normalize()
        up = up.normalize()
    }

    open fun onUpdate(elapsedTime: Double, input: InputState) {
        position = position.mul(velocity.mul(elapsedTime.toFloat()))


    }
}
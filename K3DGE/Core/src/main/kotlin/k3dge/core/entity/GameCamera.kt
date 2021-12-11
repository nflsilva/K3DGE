package k3dge.core.entity

import k3dge.ui.InputState
import org.joml.Vector3f

open class GameCamera(
    var position: Vector3f = Vector3f(0.0f, 0.0f, 0.0f),
    var forward: Vector3f = Vector3f(0.0f, 0.0f, -1.0f),
    var up: Vector3f = Vector3f(0.0f, 1.0f, 0.0f)) {

    open fun onUpdate(elapsedTime: Double, input: InputState) {}
}
package batch

import k3dge.core.entity.GameCamera
import k3dge.ui.dto.InputState
import org.joml.Vector3f

class Camera(position: Vector3f,
             forward: Vector3f,
             up: Vector3f): GameCamera(position, forward, up) {

    private var velocity: Vector3f = Vector3f(0.0f, 0.0f, 0.0f)

    override fun onUpdate(elapsedTime: Double, input: InputState) {

        if(input.pressedKeys.contains(65)) {
            velocity.x -= 5.0f
        }
        else if(input.pressedKeys.contains(68)) {
            velocity.x += 5.0f
        }
        if(input.pressedKeys.contains(87)) {
            velocity.z -= 5.0f
        }
        else if(input.pressedKeys.contains(83)) {
            velocity.z += 5.0f
        }

        if (velocity.x != 0.0f) {
            position.x += velocity.x * elapsedTime.toFloat()
            velocity.x = (velocity.x / 1.05f).toInt().toFloat()
        }
        if (velocity.z != 0.0f) {
            position.z += velocity.z * elapsedTime.toFloat()
            velocity.z = (velocity.z / 1.05f).toInt().toFloat()
        }
    }
}
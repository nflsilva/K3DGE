import k3dge.core.entity.GameCamera
import k3dge.ui.InputState
import org.joml.Vector3f

class Camera(position: Vector3f,
             forward: Vector3f,
             up: Vector3f): GameCamera(position, forward, up) {

    private var velocity: Vector3f = Vector3f(0.0f, 0.0f, 0.0f)

    override fun onUpdate(elapsedTime: Double, input: InputState) {
        val v = velocity.mul(elapsedTime.toFloat())
        position = position.add(v)

        if(input.pressedKeys.contains(65)) {
            velocity.x -= 15.0f
        }
        else if(input.pressedKeys.contains(68)) {
            velocity.x += 15.0f
        }

        if (velocity.x != 0.0f) {
            println(velocity.x)
            velocity.x = (velocity.x / 1.05f).toInt().toFloat()
        }


    }


}
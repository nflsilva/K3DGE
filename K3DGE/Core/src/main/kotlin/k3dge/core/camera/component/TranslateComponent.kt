package k3dge.core.camera.component

import k3dge.core.camera.GameCamera
import k3dge.core.common.ComponentSignal
import k3dge.ui.dto.InputStateData
import org.joml.Vector3f

class TranslateComponent(private var speed: Float): CameraComponent() {

    private var moveSpeed: Vector3f = Vector3f(0.0f, 0.0f, 0.0f)

    override fun onUpdate(camera: GameCamera, elapsedTime: Double, input: InputStateData) {
        if(input.isKeyPressed(InputStateData.KEY_A)) {
            moveSpeed.x -= speed
        }
        else if(input.isKeyPressed(InputStateData.KEY_D)) {
            moveSpeed.x += speed
        }
        if(input.isKeyPressed(InputStateData.KEY_W)) {
            moveSpeed.z -= speed
        }
        else if(input.isKeyPressed(InputStateData.KEY_S)) {
            moveSpeed.z += speed
        }

        var willSendStopSignal = false
        if (moveSpeed.x != 0.0f) {
            val delta = moveSpeed.x * elapsedTime.toFloat()
            val direction = Vector3f(camera.up).cross(camera.forward)
            camera.move(direction, -delta)
            moveSpeed.x = slowDown(moveSpeed.x)
            willSendStopSignal = true
        }
        if (moveSpeed.z != 0.0f) {
            val delta = moveSpeed.z * elapsedTime.toFloat()
            val direction = Vector3f(camera.forward.x, 0.0f, camera.forward.z)
            camera.move(direction, -delta)
            moveSpeed.z = slowDown(moveSpeed.z)
            willSendStopSignal = true
        }

        if(willSendStopSignal){
            camera.onSignal(ComponentSignal(uid, ComponentSignal.Type.STOP_ROTATION))
        }

        camera.lookForward()
    }
}
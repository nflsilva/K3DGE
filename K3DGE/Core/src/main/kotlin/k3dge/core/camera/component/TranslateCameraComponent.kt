package k3dge.core.camera.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.ComponentSignal
import k3dge.core.common.dto.UpdateData
import k3dge.tools.Util
import k3dge.ui.dto.InputStateData
import org.joml.Vector3f

class TranslateCameraComponent(private var speed: Float): BaseComponent() {

    private var moveSpeed: Vector3f = Vector3f(0.0f, 0.0f, 0.0f)

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.camera?.let { camera ->
            if(context.input.isKeyPressed(InputStateData.KEY_A)) {
                moveSpeed.x -= speed
            }
            else if(context.input.isKeyPressed(InputStateData.KEY_D)) {
                moveSpeed.x += speed
            }
            if(context.input.isKeyPressed(InputStateData.KEY_W)) {
                moveSpeed.z -= speed
            }
            else if(context.input.isKeyPressed(InputStateData.KEY_S)) {
                moveSpeed.z += speed
            }

            var willSendStopSignal = false
            if (moveSpeed.x != 0.0f) {
                val delta = moveSpeed.x * context.elapsedTime.toFloat()
                val direction = Vector3f(camera.up).cross(camera.forward)
                camera.move(direction, -delta)
                moveSpeed.x = Util.computeLogDecrement(moveSpeed.x)
                willSendStopSignal = true
            }
            if (moveSpeed.z != 0.0f) {
                val delta = moveSpeed.z * context.elapsedTime.toFloat()
                val direction = Vector3f(camera.forward.x, 0.0f, camera.forward.z)
                camera.move(direction, -delta)
                moveSpeed.z = Util.computeLogDecrement(moveSpeed.z)
                willSendStopSignal = true
            }

            if(willSendStopSignal){
                camera.onSignal(ComponentSignal(uid, ComponentSignal.Type.STOP_ROTATION))
            }

            camera.lookForward()
        }
    }
}
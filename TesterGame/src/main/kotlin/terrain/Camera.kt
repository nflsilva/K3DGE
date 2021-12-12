package terrain

import k3dge.core.entity.GameCamera
import k3dge.tools.Log
import k3dge.tools.Util
import k3dge.tools.Util.Companion.clamp
import k3dge.ui.dto.InputStateData
import org.joml.Vector2f
import org.joml.Vector3f
import kotlin.math.abs

class Camera(position: Vector3f,
             forward: Vector3f,
             up: Vector3f): GameCamera(position, forward, up) {

    private var moveSpeed: Vector3f = Vector3f(0.0f, 0.0f, 0.0f)
    private var rotateSpeed: Vector2f = Vector2f(0.0f, 0.0f)
    private var zoomSpeed: Float = 0.0F
    private var rotationLookAt: Vector3f? = null

    override fun onUpdate(elapsedTime: Double, input: InputStateData) {

        if(input.isKeyPressed(InputStateData.KEY_A)) {
            moveSpeed.x -= 5.0f
        }
        else if(input.isKeyPressed(InputStateData.KEY_D)) {
            moveSpeed.x += 5.0f
        }
        if(input.isKeyPressed(InputStateData.KEY_W)) {
            moveSpeed.z -= 5.0f
        }
        else if(input.isKeyPressed(InputStateData.KEY_S)) {
            moveSpeed.z += 5.0f
        }
        if(input.scrollY != 0){
            zoomSpeed += 25.0f * input.scrollY.toFloat()
        }
        if(input.isMousePressed(InputStateData.BUTTON_LEFT)) {
            if(abs(input.dragDeltaY) > dragThreashold) {
                rotateSpeed.y = -input.dragDeltaY.toFloat()
                if(rotationLookAt == null) {
                    rotationLookAt = Vector3f(5.5f, 0.5f, 5.5f)
                }
            }
            if(abs(input.dragDeltaX) > dragThreashold) {
                rotateSpeed.x = -input.dragDeltaX.toFloat()
                if(rotationLookAt == null) {
                    rotationLookAt = Vector3f(5.5f, 0.5f, 5.5f)
                }
            }
        }

        if (moveSpeed.x != 0.0f) {
            val delta = moveSpeed.x * elapsedTime.toFloat()
            move(Vector3f(up).cross(forward), -delta)
            moveSpeed.x = (moveSpeed.x / 1.05f).toInt().toFloat()
            rotateSpeed.x = 0.0f
        }
        if (moveSpeed.z != 0.0f) {
            val delta = moveSpeed.z * elapsedTime.toFloat()
            move(Vector3f(forward.x, 0.0f, forward.z), -delta)
            moveSpeed.z = (moveSpeed.z / 1.05f).toInt().toFloat()
            rotateSpeed.x = 0.0f
        }
        if (zoomSpeed != 0.0f) {
            val delta = zoomSpeed * elapsedTime.toFloat()
            move(forward, delta)
            zoomSpeed = (zoomSpeed / 1.05f).toInt().toFloat()
            rotateSpeed.x = 0.0f
        }

        if (rotateSpeed.x != 0.0f){
            rotateAroundPoint(rotateSpeed.x * elapsedTime.toFloat(),
                Vector3f(0.0f, 1.0f, 0.0f),
                rotationLookAt!!)
            rotateSpeed.x = (rotateSpeed.x / 1.05f).toInt().toFloat()
        }
        if (rotateSpeed.y != 0.0f){
            if((forward.y >= -0.75 && rotateSpeed.y < 0) ||
                (forward.y <= 0.1 && rotateSpeed.y > 0)) {
                rotateAroundPoint(rotateSpeed.y * elapsedTime.toFloat(),
                    Vector3f(forward).cross(up),
                    rotationLookAt!!)
            }
            rotateSpeed.y = (rotateSpeed.y / 1.05f).toInt().toFloat()
        }

        computeLookAt()
    }


    private fun computeLookAt(){
        lookAt = Vector3f(position).add(forward)
    }

    companion object {
        private val dragThreashold = 25
    }

}
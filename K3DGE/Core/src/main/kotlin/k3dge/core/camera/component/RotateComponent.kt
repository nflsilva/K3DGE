package k3dge.core.camera.component

import k3dge.core.camera.GameCamera
import k3dge.core.common.ComponentSignal
import k3dge.ui.dto.InputStateData
import org.joml.Vector2f
import org.joml.Vector3f

class RotateComponent(
    private val speed: Float,
    private val upperLimit: Float,
    private val lowerLimit: Float): CameraComponent() {

    private var rotateSpeed: Vector2f = Vector2f(0.0f)
    private var rotationLookAt: Vector3f = Vector3f(0.0f)
    private var isRotating: Boolean = false

    override fun onUpdate(camera: GameCamera, elapsedTime: Double, input: InputStateData) {
        if(input.isMousePressed(InputStateData.BUTTON_LEFT)) {
            rotateSpeed.x = -input.dragDeltaX.toFloat() * speed
            rotateSpeed.y = -input.dragDeltaY.toFloat() * speed
            if(!isRotating){
                val nPlane = Vector3f(0.0f, 1.0f, 0.0f)
                val xPlane = Vector3f(0.0f, 0.0f, 0.0f)
                val mRay = Vector3f(camera.forward)
                val t = Vector3f(nPlane).dot(xPlane.sub(camera.position)) / Vector3f(nPlane).dot(mRay)
                // TODO: I need to fix this...
                if(t > 0 && t < 1000){
                    rotationLookAt = Vector3f(camera.forward).mul(t).add(camera.position)
                    isRotating = true
                }
            }
        }
        if (rotateSpeed.x != 0.0f && isRotating){
            camera.rotateAroundPoint(
                rotateSpeed.x * elapsedTime.toFloat(),
                Vector3f(0.0f, 1.0f, 0.0f),
                rotationLookAt)
            rotateSpeed.x = slowDown(rotateSpeed.x)
        }
        if (rotateSpeed.y != 0.0f && isRotating){

            val isWithinYLimits = (camera.forward.y >= upperLimit && rotateSpeed.y < 0)
            val isWithinXLimits = (camera.forward.y <= lowerLimit && rotateSpeed.y > 0)

            rotateSpeed.y = if(isWithinYLimits || isWithinXLimits) {
                camera.rotateAroundPoint(
                    rotateSpeed.y * elapsedTime.toFloat(),
                    Vector3f(camera.forward).cross(camera.up),
                    rotationLookAt)
                slowDown(rotateSpeed.y)
            }
            else {
                0.0f
            }
        }
        isRotating = rotateSpeed.x != 0.0f || rotateSpeed.y != 0.0f
        camera.lookForward()
    }
    override fun onSignal(signal: ComponentSignal) {
        if(signal.senderId != uid){
            when(signal.type){
                ComponentSignal.Type.STOP_ROTATION -> stopRotating()
            }
        }
    }
    private fun stopRotating(){
        isRotating = false
        rotateSpeed = Vector2f(0.0f)
    }
}
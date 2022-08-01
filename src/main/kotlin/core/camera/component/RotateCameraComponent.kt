package core.camera.component

import core.common.Component
import core.common.ComponentSignal
import core.common.dto.UpdateData
import org.joml.Vector2f
import org.joml.Vector3f
import tools.common.Util
import ui.dto.InputStateData

class RotateCameraComponent(
    private val speed: Float,
    private val upperLimit: Float,
    private val lowerLimit: Float
) : Component() {

    private var rotateSpeed: Vector2f = Vector2f(0.0f)
    private var rotationLookAt: Vector3f = Vector3f(0.0f)
    private var isRotating: Boolean = false

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setSignalObserver { signal -> onSignal(signal) }
    }

    private fun onUpdate(context: UpdateData) {
        context.camera?.let { camera ->
            val input = context.input
            val elapsedTime = context.elapsedTime

            if (input.isMousePressed(InputStateData.BUTTON_LEFT)) {
                rotateSpeed.x = -input.dragDeltaX.toFloat() * speed
                rotateSpeed.y = -input.dragDeltaY.toFloat() * speed
                if (!isRotating) {
                    val nPlane = Vector3f(0.0f, 1.0f, 0.0f)
                    val xPlane = Vector3f(0.0f, 0.0f, 0.0f)
                    val mRay = Vector3f(camera.forward)
                    val t = Vector3f(nPlane).dot(xPlane.sub(camera.transform.getPos())) / Vector3f(nPlane).dot(mRay)

                    // NOTE: t may be infinite or invalid.
                    // This check will prevent unwanted behaviours when this is the case.
                    if (t > 0 && t < Int.MAX_VALUE) {
                        rotationLookAt = Vector3f(camera.forward).mul(t).add(camera.transform.getPos())
                        isRotating = true
                    }
                }
            }

            if (rotateSpeed.x != 0.0f && isRotating) {
                camera.rotateAroundPoint(
                    rotateSpeed.x * elapsedTime.toFloat(),
                    Vector3f(0.0f, 1.0f, 0.0f),
                    rotationLookAt
                )
                rotateSpeed.x = Util.computeLogDecrement(rotateSpeed.x)
            }

            if (rotateSpeed.y != 0.0f && isRotating) {

                val isWithinYLimits = (camera.forward.y >= upperLimit && rotateSpeed.y < 0)
                val isWithinXLimits = (camera.forward.y <= lowerLimit && rotateSpeed.y > 0)

                rotateSpeed.y = if (isWithinYLimits || isWithinXLimits) {
                    camera.rotateAroundPoint(
                        rotateSpeed.y * elapsedTime.toFloat(),
                        Vector3f(camera.forward).cross(camera.up),
                        rotationLookAt
                    )
                    Util.computeLogDecrement(rotateSpeed.y)
                } else {
                    0.0f
                }
            }

            if (rotateSpeed.x != 0.0f || rotateSpeed.y != 0.0f) {
                isRotating = true
                camera.forward = Vector3f(rotationLookAt).sub(camera.transform.getPos())
                camera.forward.normalize()
            }
            camera.lookForward()
        }
    }

    private fun onSignal(signal: ComponentSignal) {
        if (signal.senderId != uid) {
            when (signal.type) {
                ComponentSignal.Type.STOP_ROTATION -> stopRotating()
            }
        }
    }

    private fun stopRotating() {
        isRotating = false
        rotateSpeed = Vector2f(0.0f)
    }
}
package k3dge.core.camera.component

import k3dge.core.camera.GameCamera
import k3dge.ui.dto.InputStateData
import org.joml.Vector3f

class ZoomComponent(
    private val speed: Float,
    private val nearLimit: Float? = null,
    private val farLimit: Float? = null): CameraComponent() {

    private var zoomSpeed: Float = 0.0F

    override fun onUpdate(camera: GameCamera, elapsedTime: Double, input: InputStateData) {
        if(input.scrollY != 0){
            zoomSpeed += speed * input.scrollY.toFloat()
        }
        if (zoomSpeed != 0.0f) {

            val nPlane = Vector3f(0.0f, 1.0f, 0.0f)
            val xPlane = Vector3f(0.0f, 0.0f, 0.0f)
            val mRay = Vector3f(camera.forward)
            val t = Vector3f(nPlane).dot(xPlane.sub(camera.position)) / Vector3f(nPlane).dot(mRay)
            val delta = zoomSpeed * elapsedTime.toFloat()

            var isWithinNearLimits = true
            var isWithinFarLimits = true
            nearLimit?.let { isWithinNearLimits = (t > it && delta > 0) }
            farLimit?.let { isWithinFarLimits = (t < farLimit && delta < 0) }

            zoomSpeed = if(isWithinNearLimits || isWithinFarLimits) {
                camera.move(camera.forward, delta)
                slowDown(zoomSpeed)
            } else {
                0.0f
            }

        }
    }
}
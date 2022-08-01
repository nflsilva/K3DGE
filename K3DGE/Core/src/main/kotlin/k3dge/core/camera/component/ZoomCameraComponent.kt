package k3dge.core.camera.component

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.tools.Util
import org.joml.Vector3f

class ZoomCameraComponent(private val speed: Float,
                          private val nearLimit: Float? = null,
                          private val farLimit: Float? = null): Component() {

    private var zoomSpeed: Float = 0.0F

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateData) {
        context.camera?.let { camera ->
            if(context.input.scrollY != 0){
                zoomSpeed += speed * context.input.scrollY.toFloat()
            }
            if (zoomSpeed != 0.0f) {

                val nPlane = Vector3f(0.0f, 1.0f, 0.0f)
                val xPlane = Vector3f(0.0f, 0.0f, 0.0f)
                val mRay = Vector3f(camera.forward)
                val t = Vector3f(nPlane).dot(xPlane.sub(camera.transform.getPos())) / Vector3f(nPlane).dot(mRay)
                val delta = zoomSpeed * context.elapsedTime.toFloat()

                var isWithinNearLimits = true
                var isWithinFarLimits = true
                nearLimit?.let { isWithinNearLimits = (t > it && delta > 0) }
                farLimit?.let { isWithinFarLimits = (t < farLimit && delta < 0) }

                zoomSpeed = if(isWithinNearLimits || isWithinFarLimits) {
                    camera.move(camera.forward, delta)
                    Util.computeLogDecrement(zoomSpeed)
                } else {
                    0.0f
                }
            }
        }
    }
}
package k3dge.core.camera.component

import k3dge.core.camera.GameCamera
import k3dge.core.camera.component.CameraComponent.Companion.slowDown
import k3dge.core.common.ComponentSignal
import k3dge.ui.dto.InputStateData

class ZoomComponent(private val speed: Float): CameraComponent() {

    private var zoomSpeed: Float = 0.0F

    override fun onUpdate(camera: GameCamera, elapsedTime: Double, input: InputStateData) {
        if(input.scrollY != 0){
            zoomSpeed += speed * input.scrollY.toFloat()
        }
        if (zoomSpeed != 0.0f) {
            val delta = zoomSpeed * elapsedTime.toFloat()
            camera.move(camera.forward, delta)
            zoomSpeed = slowDown(zoomSpeed)
        }
    }
}
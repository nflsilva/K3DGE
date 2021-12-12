package k3dge.core.camera.component

import k3dge.core.camera.GameCamera
import k3dge.core.common.ComponentSignal
import k3dge.ui.dto.InputStateData
import java.util.*
import kotlin.math.abs
import kotlin.math.log

abstract class CameraComponent {

    protected val uid: String = UUID.randomUUID().toString()

    open fun cleanUp(){}
    abstract fun onUpdate(camera: GameCamera, elapsedTime: Double, input: InputStateData)
    open fun onSignal(signal: ComponentSignal){}

    companion object {
        fun slowDown(value: Float): Float {
            val magnitude = abs(value).toDouble()
            val reductionFactor = (log(magnitude, 10.0) + 1).toFloat()
            return if(magnitude < 0.5){ 0f }
            else if(value > 0) { value - reductionFactor }
            else { value + reductionFactor }
        }
    }
}
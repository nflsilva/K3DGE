package k3dge.core.camera.component

import k3dge.core.camera.GameCamera
import k3dge.core.common.BaseComponent
import k3dge.core.common.BaseEntity
import k3dge.core.common.ComponentSignal
import k3dge.ui.dto.InputStateData
import kotlin.math.abs
import kotlin.math.log

abstract class CameraComponent : BaseComponent() {

    override fun cleanUp(){}
    override fun onSignal(signal: ComponentSignal){}
    final override fun onUpdate(entity: BaseEntity, elapsedTime: Double, input: InputStateData) {
        val camera = entity as? GameCamera ?: return
        onUpdate(camera, elapsedTime, input)
    }
    abstract fun onUpdate(camera: GameCamera, elapsedTime: Double, input: InputStateData)

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
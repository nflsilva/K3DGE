package k3dge.core.light.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.UpdateContext
import k3dge.core.light.Light
import k3dge.ui.dto.InputStateData
import org.joml.Vector3f
import org.joml.Vector4f
import java.lang.Float.max
import kotlin.math.abs

class ColorLightComponent: BaseComponent() {

    private val color0 = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
    private val color1 = Vector4f(250f/255f, 114f/255f, 55/255f, 1.0f)

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }
    private fun onUpdate(context: UpdateContext) {
        context.light?.let { light ->
            val floorNormal = Vector3f(0.0f, 1.0f, 0.0f)
            val floorParallel = Vector3f(1.0f, 0.0f, 0.0f)
            val intensity = max(0.0f, light.position.dot(floorNormal))
            val colorGradient = abs(light.position.dot(floorParallel))

            //Log.d("${light.position} - $intensity - $colorGradient")
            light.color = Vector4f(color0)
                .mul(1 - colorGradient)
                .add(Vector4f(color1).mul(colorGradient))
                .mul(intensity)
        }
    }
}
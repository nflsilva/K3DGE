package core.light.component

import core.common.Component
import core.common.dto.UpdateData
import org.joml.Vector3f
import org.joml.Vector4f
import java.lang.Float.max
import kotlin.math.abs
import kotlin.math.min

class ColorLightComponent : Component() {

    private val color0 = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
    private val color1 = Vector4f(250f / 255f, 114f / 255f, 55 / 255f, 1.0f)

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData) {
        context.light?.let { light ->
            val floorNormal = Vector3f(0.0f, 1.0f, 0.0f)
            val dotPositionNormal = Vector3f(light.transform.getPos()).normalize().dot(floorNormal)

            val intensity = min(1.0f, max(-light.transform.getPos().y, 0.0f))
            val colorGradient = abs(dotPositionNormal)

            //Log.d("${light.position.y} || $intensity || $colorGradient")

            val newColor = Vector4f(color0).mul(colorGradient)
                .add(Vector4f(color1).mul(1 - colorGradient))
                .mul(intensity)

            light.color = newColor
            context.graphics.setBackgroundColor(newColor)

        }
    }
}
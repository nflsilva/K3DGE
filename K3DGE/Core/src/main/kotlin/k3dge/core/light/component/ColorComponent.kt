package k3dge.core.light.component

import k3dge.core.light.GameLight
import k3dge.tools.Log
import k3dge.ui.dto.InputStateData
import org.joml.Vector3f
import org.joml.Vector4f
import java.lang.Float.max

class ColorComponent: LightComponent() {

    private val color0 = Vector4f(0.0f, 0.0f, 1.0f, 1.0f)
    private val color1 = Vector4f(250f/255f, 214f/255f, 165/255f, 1.0f)

    override fun onUpdate(light: GameLight, elapsedTime: Double, input: InputStateData) {
        val floorNormal = Vector3f(0.0f, 1.0f, 0.0f)
        val floorParallel = Vector3f(1.0f, 0.0f, 0.0f)
        val intensity = max(0.0f, light.position.dot(floorNormal))
        val colorGradient = max(0.0f, light.position.dot(floorParallel))

        //Log.d("${light.position} - $intensity")
        Vector4f(color0)
            .mul(1 - colorGradient)
            .add(Vector4f(color1).mul(colorGradient))
            .mul(intensity)
    }
}
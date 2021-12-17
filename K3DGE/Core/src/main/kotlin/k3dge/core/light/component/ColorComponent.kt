package k3dge.core.light.component

import k3dge.core.light.GameLight
import k3dge.tools.Log
import k3dge.ui.dto.InputStateData
import org.joml.Vector3f
import org.joml.Vector4f

class ColorComponent: LightComponent() {

    private val color0 = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
    private val color1 = Vector4f(250f/255f, 214f/255f, 165/255f, 1.0f)

    override fun onUpdate(light: GameLight, elapsedTime: Double, input: InputStateData) {
        val floorNormal = Vector3f(0.0f, 1.0f, 0.0f)
        val dot = light.position.dot(floorNormal)
        light.color = if(dot < 0) {
            Vector4f(0f)
        }
        else {
            Vector4f(color0).mul(1 - dot).add(Vector4f(color1).mul(dot))
        }
    }
}
package k3dge.core.light.component

import k3dge.core.light.GameLight
import k3dge.tools.Util
import k3dge.ui.dto.InputStateData
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

class LightRotateComponent(private var speed: Float): LightComponent() {

    override fun onUpdate(light: GameLight, elapsedTime: Double, input: InputStateData) {
        val rotationMatrix = Matrix4f().rotate(Util.degreeToRadian(speed), Vector3f(0.0f, 0.0f, 1.0f))
        val newPosition = Vector4f(light.position.x, light.position.y, light.position.z, 1.0F)
            .mul(rotationMatrix)
        light.position.x = newPosition.x
        light.position.y = newPosition.y
        light.position.z = newPosition.z
    }
}
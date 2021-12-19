package k3dge.core.light.component

import k3dge.core.common.BaseComponent
import k3dge.core.common.dto.UpdateData
import k3dge.tools.Util
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

class LightRotateLightComponent(private var speed: Float): BaseComponent() {

    init {
        setUpdateObserver { context -> onUpdate(context)   }
    }
    private fun onUpdate(context: UpdateData) {
        context.light?.let { light ->
            val rotationMatrix = Matrix4f().rotate(Util.degreeToRadian(speed), Vector3f(0.0f, 0.0f, 1.0f))
            val newPosition = Vector4f(light.position.x, light.position.y, light.position.z, 1.0F)
                .mul(rotationMatrix)
            light.position.x = newPosition.x
            light.position.y = newPosition.y
            light.position.z = newPosition.z
        }
    }
}
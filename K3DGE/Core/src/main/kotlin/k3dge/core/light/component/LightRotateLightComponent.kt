package k3dge.core.light.component

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.tools.Util
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

class LightRotateLightComponent(private var speed: Float): Component() {

    init {
        setUpdateObserver { context -> onUpdate(context)   }
    }
    private fun onUpdate(context: UpdateData) {
        context.light?.let { light ->

            val rotationMatrix = Matrix4f().rotate(Util.degreeToRadian(speed), Vector3f(0.0f, 0.0f, 1.0f))
            val newPosition = Vector4f(light.transform.getPos(), 1.0F).mul(rotationMatrix)

            light.transform.setPosition(newPosition)
        }
    }
}
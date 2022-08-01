package core.light.component

import core.common.Component
import core.common.dto.UpdateData
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import tools.common.Util

class LightRotateLightComponent(private var speed: Float) : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData) {
        context.light?.let { light ->

            val rotationMatrix = Matrix4f().rotate(Util.degreeToRadian(speed), Vector3f(0.0f, 0.0f, 1.0f))
            val newPosition = Vector4f(light.transform.getPos(), 1.0F).mul(rotationMatrix)

            light.transform.setPosition(newPosition)
        }
    }
}
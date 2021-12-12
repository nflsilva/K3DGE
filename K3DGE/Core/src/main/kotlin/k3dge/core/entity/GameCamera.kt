package k3dge.core.entity

import k3dge.tools.Util
import k3dge.ui.dto.InputStateData
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f

open class GameCamera(
    var position: Vector3f = Vector3f(0.0f, 0.0f, 0.0f),
    var forward: Vector3f = Vector3f(0.0f, 0.0f, -1.0f),
    var up: Vector3f = Vector3f(0.0f, 1.0f, 0.0f),
    var lookAt: Vector3f = Vector3f(forward).add(position)) {

    fun move(direction: Vector3f, delta: Float){
        position.add(Vector3f(direction).mul(delta))
    }
    fun rotateAroundPoint(delta: Float, axis: Vector3f, point: Vector3f){

        val angle = Util.degreeToRadian(delta)
        val q = Quaternionf().rotateAxis(angle, axis)
        val rotation = Matrix4f().rotateAround(q, point.x, point.y, point.z)

        val newPosition = Vector4f(position.x, position.y, position.z, 1.0F)
            .mul(rotation)

        position.x = newPosition.x
        position.y = newPosition.y
        position.z = newPosition.z

        forward = Vector3f(point).sub(position)
        forward.normalize()
    }
    open fun onUpdate(elapsedTime: Double, input: InputStateData) {}
}
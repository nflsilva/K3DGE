package k3dge.core.entity

import k3dge.core.common.BaseEntity
import k3dge.render.common.dto.TransformData
import k3dge.render.renderer3d.dto.Transform3DData
import k3dge.tools.Util
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f

open class Entity3D(transform: TransformData): BaseEntity(transform) {

    constructor(position: Vector3f, rotation: Vector3f, scale: Vector3f) :
            this(Transform3DData(position, rotation, scale))

    fun rotateAroundPoint(delta: Float, axis: Vector3f, point: Vector3f){
        val angle = Util.degreeToRadian(delta)
        val q = Quaternionf().rotateAxis(angle, axis)
        val rotation = Matrix4f().rotateAround(q, point.x, point.y, point.z)

        val currentPosition = transform.getPos()
        val newPosition = Vector4f(currentPosition, 1.0f).mul(rotation)

        transform.setPosition(newPosition)
    }

}
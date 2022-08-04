package render.renderer3d.dto

import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import render.common.dto.TransformData

class Transform3DData(
    val position: Vector3f,
    val rotation: Vector3f = Vector3f().zero(),
    val scale: Vector3f = Vector3f(1f, 1f, 1f)
) : TransformData {

    override fun setPosition(vec: Vector4f) {
        position.x = vec.x
        position.y = vec.y
        position.z = vec.z
    }

    override fun getPos(): Vector3f {
        return position
    }

    override fun translate(vec: Vector2f) {
        position.add(Vector3f(vec.x, vec.y, 0.0f))
    }

    override fun translate(vec: Vector3f) {
        position.add(vec)
    }

    override fun rotate(vec: Float) {
        rotation.add(Vector3f(vec))
    }

    override fun rotate(vec: Vector3f) {
        rotation.add(vec)
    }

    override fun scale(vec: Vector2f) {
        scale.add(Vector3f(vec.x, vec.y, 1.0f))
    }

    override fun scale(vec: Vector3f) {
        scale.add(vec)
    }

    override fun getScal(): Vector3f {
        return scale
    }

}
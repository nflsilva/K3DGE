package render.renderer2d.dto

import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import render.common.dto.TransformData

class Transform2DData(
    val position: Vector2f,
    var rotation: Float = 0f,
    val scale: Vector2f = Vector2f(1.0f, 1.0f)
) : TransformData {

    override fun setPosition(vec: Vector4f) {
        position.x = vec.x
        position.y = vec.y
    }

    override fun getPos(): Vector3f {
        return Vector3f(position.x, position.y, 0.0f)
    }

    override fun translate(vec: Vector2f) {
        position.add(vec)
    }

    override fun translate(vec: Vector3f) {
        position.add(Vector2f(vec.x, vec.y))
    }

    override fun rotate(vec: Float) {
        rotation += vec
    }

    override fun rotate(vec: Vector3f) {
        position.add(Vector2f(vec.x, vec.y))
    }

    override fun scale(vec: Vector2f) {
        scale.add(vec)
    }

    override fun scale(vec: Vector3f) {
        scale.add(Vector2f(vec.x, vec.y))
    }

}
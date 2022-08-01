package render.common.dto

import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f

interface TransformData {

    //TODO: Maybe refactor this mess? How should I implement these?
    fun setPosition(vec: Vector4f)
    fun getPos(): Vector3f

    fun translate(vec: Vector2f)
    fun translate(vec: Vector3f)

    fun rotate(vec: Float)
    fun rotate(vec: Vector3f)

    fun scale(vec: Vector2f)
    fun scale(vec: Vector3f)

}
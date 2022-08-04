package physics.physics2d.dto

import org.joml.Vector2f

abstract class CollisionBox(
    val top: Float,
    val bottom: Float,
    val left: Float,
    val right: Float) {

    abstract fun isCollidingWith(otherBox: CollisionBox): Vector2f?
}
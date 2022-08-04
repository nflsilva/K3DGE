package physics.physics2d.dto

import org.joml.Vector2f

class RectangleCollisionBox(
    x: Float,
    y: Float,
    width: Float,
    height: Float): CollisionBox(y + height / 2, y - height / 2, x, x + width / 2) {

    override fun isCollidingWith(otherBox: CollisionBox): Vector2f? {
        println("${top >= otherBox.bottom && bottom <= otherBox.top}")
        println("$top ${otherBox.bottom}")

        //val horizontalCollision = right >= otherBox.left || otherBox.right >= left

        val bottomCollision = top >= otherBox.bottom && bottom <= otherBox.top
        if(bottomCollision){ return Vector2f(0f, otherBox.top) }

        //val topCollision = top >= otherBox.bottom
        //if(topCollision){ return Vector2f(0f, otherBox.bottom) }

        return null
    }

}

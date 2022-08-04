package physics.physics2d.dto

import java.util.*

interface CollisionContextDelegate {
    fun onCollisionDetected(collisionResult: CollisionResult)
}
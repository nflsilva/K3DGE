package physics.physics2d.dto

import java.util.*

class CollisionContext(
    val componentId: UUID,
    val collisionBox: CollisionBox,
    private val delegate: CollisionContextDelegate) {

    fun didCollideWith(result: CollisionResult){
        delegate.onCollisionDetected(result)
    }

}
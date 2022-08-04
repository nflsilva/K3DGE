package physics.physics2d

import physics.physics2d.dto.CollisionContext
import physics.physics2d.dto.CollisionResult

class PhysicsEngine2D {

    private val collisionContexts: MutableList<CollisionContext> = mutableListOf()

    fun onStart() {}

    fun onFrame() {}

    fun onUpdate() {
        processCollisions()
        collisionContexts.clear()
    }

    fun onCleanUp() {}

    fun registerCollisionContext(context: CollisionContext){
        collisionContexts.add(context)
    }

    private fun processCollisions(){
        //TODO: optimize this
        collisionContexts.forEach { c0 ->
            collisionContexts.forEach { c1 ->
                if(c0 != c1){
                    val collisionPoint = c0.collisionBox.isCollidingWith(c1.collisionBox)
                    if(collisionPoint != null){
                        c0.didCollideWith(CollisionResult(c1.componentId, collisionPoint))
                        c1.didCollideWith(CollisionResult(c0.componentId, collisionPoint))
                    }
                    println(" ")
                }
            }
        }
    }

}
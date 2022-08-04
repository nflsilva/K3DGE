package core.entity.component2d

import core.common.Component
import core.common.ComponentSignal
import core.common.dto.UpdateData
import physics.physics2d.dto.CollisionContextDelegate
import physics.physics2d.dto.CollisionResult
import physics.physics2d.dto.RectangleCollisionBox

class Collision2DBoxComponent(
    private val onCollisionCallback: (collisions: MutableList<CollisionResult>) -> Unit)
    : Component(), CollisionContextDelegate {

    private val collisions: MutableList<CollisionResult> = mutableListOf()

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setSignalObserver { signal -> onSignal(signal) }
    }

    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->

            val pos = entity.transform.getPos()
            val size = entity.transform.getScal()
            val box = RectangleCollisionBox(pos.x, pos.y, size.x, size.y)
            context.physics.register2DCollisionBox(uid, box, this)
            collisions.clear()

        }
    }

    private fun onSignal(signal: ComponentSignal) {
        if (signal.senderId != uid) {

        }
    }

    override fun onCollisionDetected(collisionResult: CollisionResult) {
        collisions.add(collisionResult)
        onCollisionCallback(collisions)
    }

}
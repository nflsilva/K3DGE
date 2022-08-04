package physics

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import physics.physics2d.PhysicsEngine2D
import physics.physics2d.dto.CollisionBox
import physics.physics2d.dto.CollisionContext
import physics.physics2d.dto.CollisionContextDelegate
import tools.configuration.EngineConfiguration
import java.util.UUID

class PhysicsEngine(configuration: EngineConfiguration) {

    private val physics2DEngine = PhysicsEngine2D()

    fun register2DCollisionBox(
        componentId: UUID,
        collisionBox: CollisionBox,
        delegate: CollisionContextDelegate) {

        val context = CollisionContext(componentId, collisionBox, delegate)
        physics2DEngine.registerCollisionContext(context)
    }

    fun onStart() {
    }

    fun onFrame() {
        physics2DEngine.onFrame()
    }

    fun onUpdate() {
        physics2DEngine.onUpdate()
    }

    fun onCleanUp() {
        physics2DEngine.onCleanUp()
    }

}
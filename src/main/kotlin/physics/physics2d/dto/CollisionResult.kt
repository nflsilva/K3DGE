package physics.physics2d.dto

import org.joml.Vector2f
import java.util.UUID

data class CollisionResult(
    val componentId: UUID,
    val lastValidPosition: Vector2f)
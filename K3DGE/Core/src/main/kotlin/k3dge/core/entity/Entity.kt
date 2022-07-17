package k3dge.core.entity

import k3dge.core.common.BaseEntity
import k3dge.core.entity.component.TransformEntityComponent
import k3dge.render.common.dto.TransformData
import org.joml.Vector2f
import org.joml.Vector3f

class Entity(transform: TransformEntityComponent): BaseEntity(transform) {

    constructor(position: Vector3f, rotation: Vector3f, scale: Vector3f) :
            this(TransformEntityComponent(position, rotation, scale))

    constructor(position: Vector2f, rotation: Float, scale: Vector2f) :
            this(Vector3f(position.x, position.y, 0.0f),
                Vector3f(0.0f, 0.0f, rotation),
                Vector3f(scale.x, scale.y, 0.0f))
}
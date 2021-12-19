package k3dge.core.entity

import k3dge.core.common.BaseEntity
import k3dge.render.model.ShaderModel
import org.joml.Vector2f
import org.joml.Vector3f

class Entity(position: Vector3f,
             val rotation: Vector3f,
             val scale: Vector3f): BaseEntity(position) {

    constructor(position: Vector2f, rotation: Float, scale: Vector2f) :
            this(Vector3f(position.x, position.y, 0.0f),
                Vector3f(0.0f, 0.0f, rotation),
                Vector3f(scale.x, scale.y, 0.0f)) {
    }
}
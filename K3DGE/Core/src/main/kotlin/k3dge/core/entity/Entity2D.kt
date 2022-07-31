package k3dge.core.entity

import k3dge.core.common.BaseEntity
import k3dge.render.common.dto.TransformData
import k3dge.render.renderer2d.dto.Transform2DData
import org.joml.Vector2f

open class Entity2D(transform: TransformData): BaseEntity(transform) {
    constructor(position: Vector2f, rotation: Float, scale: Vector2f) :
            this(Transform2DData(position, rotation, scale))

}
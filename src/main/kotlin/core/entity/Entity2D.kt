package core.entity

import core.common.BaseEntity
import org.joml.Vector2f
import render.common.dto.TransformData
import render.renderer2d.dto.Transform2DData

open class Entity2D(transform: TransformData) : BaseEntity(transform) {
    constructor(position: Vector2f, rotation: Float, scale: Vector2f) :
            this(Transform2DData(position, rotation, scale))

}
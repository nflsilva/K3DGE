package core.entity.component2d

import core.common.Component
import core.common.dto.UpdateData
import render.common.dto.ColorData
import render.renderer2d.dto.Shape

class ShapeComponent(private val shape: Shape) : Component() {

    constructor(type: Shape.Type, color: ColorData) : this(Shape(type.value, color))

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->
            context.graphics.render2D(shape, entity.transform)
        }
    }
}
package k3dge.core.entity.component2d

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.common.dto.ColorData
import k3dge.render.renderer2d.dto.Particle
import k3dge.render.renderer2d.dto.Shape

class ShapeComponent(private val shape: Shape): Component() {

    constructor(type: Shape.Type, color: ColorData): this(Shape(type.value, color))

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            context.graphics.render2D(shape, entity.transform)
        }
    }
}
package core.entity.component2d

import core.common.Component
import core.common.dto.UpdateData
import render.renderer2d.dto.Particle

class CircleComponent(private val particleData: Particle) : Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->
            context.graphics.render2D(particleData, entity.transform)
        }
    }
}
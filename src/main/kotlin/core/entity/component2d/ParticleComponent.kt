package core.entity.component2d

import core.common.Component
import core.common.dto.UpdateData
import render.common.dto.ColorData
import render.renderer2d.dto.Particle

class ParticleComponent(private val particle: Particle) : Component() {


    constructor(type: Int, size: Float, color: ColorData) : this(Particle(type, size, color))

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData) {
        context.entity?.let { entity ->
            context.graphics.render2D(particle, entity.transform)
        }
    }
}
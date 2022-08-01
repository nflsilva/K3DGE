package k3dge.core.entity.component2d

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.common.dto.ColorData
import k3dge.render.renderer2d.dto.Particle

class ParticleComponent(private val particle: Particle): Component() {


    constructor(type: Int, size: Float, color: ColorData): this(Particle(type, size, color))

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            context.graphics.render2D(particle, entity.transform)
        }
    }
}
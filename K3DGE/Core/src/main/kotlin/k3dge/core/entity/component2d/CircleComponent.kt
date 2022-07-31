package k3dge.core.entity.component2d

import k3dge.core.common.Component
import k3dge.core.common.dto.UpdateData
import k3dge.render.renderer2d.dto.Particle
import k3dge.render.renderer2d.dto.Transform2DData

class CircleComponent(private val particleData: Particle): Component() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
    }

    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            context.graphics.render2D(particleData, entity.transform)
        }
    }
}
package k3dge.render.renderer2d.model

import k3dge.render.common.dto.TransformData
import k3dge.render.renderer2d.dto.Point

class ParticlesBatch(maxEntities: Int):
    EntityBatch(maxEntities, 1, 1) {

    companion object {
        const val POSITION_INDEX = 0
        const val SIZE_INDEX = 1
        const val TYPE_INDEX = 2
        const val COLOR_INDEX = 3
    }

    init {
        addAttributeBuffer(POSITION_INDEX, 2)
        addAttributeBuffer(SIZE_INDEX, 1)
        addAttributeBuffer(TYPE_INDEX, 1)
        addAttributeBuffer(COLOR_INDEX, 4)
    }

    fun addParticle(particle: Point, transform: TransformData) {
        addAttributeData(POSITION_INDEX, transform.position.x, transform.position.y)
        addAttributeData(SIZE_INDEX, particle.size)
        addAttributeData(TYPE_INDEX, 1f)
        addAttributeData(COLOR_INDEX, particle.color.r, particle.color.g, particle.color.b, particle.color.a)
        addIndexData(nEntities)
        nEntities += 1
    }

}
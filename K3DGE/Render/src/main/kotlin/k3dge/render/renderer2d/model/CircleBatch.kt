package k3dge.render.renderer2d.model

import k3dge.render.common.dto.TransformData
import k3dge.render.renderer2d.dto.Circle

class CircleBatch(maxEntities: Int) {

    companion object {
        const val POSITIONS_INDEX = 0
        const val RADIUS_INDEX = 1
        const val TRANSLATIONS_INDEX = 2
        const val ROTATIONS_INDEX = 3
        const val SCALES_INDEX = 4
        const val COLORS_INDEX = 5
    }

    private val batch = EntityBatch(maxEntities, 1)

    var nIndices = 0

    init {

        batch.addAttributeBuffer(POSITIONS_INDEX, 2)
        batch.addAttributeBuffer(RADIUS_INDEX, 1)
        batch.addAttributeBuffer(TRANSLATIONS_INDEX, 2)
        batch.addAttributeBuffer(ROTATIONS_INDEX, 2)
        batch.addAttributeBuffer(SCALES_INDEX, 2)
        batch.addAttributeBuffer(COLORS_INDEX, 4)

    }

    fun bind() {
        batch.bind()
    }
    fun unbind() {
        batch.unbind()
    }
    fun clear() {
        batch.clear()
        nIndices = 0
    }
    fun addCircle(circle: Circle, transform: TransformData) {

        batch.addAttributeData(POSITIONS_INDEX, circle.center.x, circle.center.y)
        batch.addAttributeData(RADIUS_INDEX, circle.radius)
        batch.addAttributeData(TRANSLATIONS_INDEX, transform.position.x, transform.position.y)
        batch.addAttributeData(ROTATIONS_INDEX, transform.position.z)
        batch.addAttributeData(SCALES_INDEX, transform.scale.x, transform.scale.y)
        batch.addAttributeData(COLORS_INDEX, circle.color.r, circle.color.g, circle.color.b, circle.color.a)
        batch.addIndexData(nIndices)
        nIndices += 1
    }

}
package k3dge.core.entity.component

import k3dge.render.common.dto.TransformData
import org.joml.Vector3f

class TransformEntityComponent(position: Vector3f,
                               rotation: Vector3f,
                               scale: Vector3f) {

    constructor(position: Vector3f): this(position, Vector3f().zero(), Vector3f().zero())

    val data: TransformData = TransformData(position, rotation, scale)

    val position: Vector3f
    get() = data.position

    val rotation: Vector3f
    get() = data.rotation

    val scale: Vector3f
    get() = data.scale

}